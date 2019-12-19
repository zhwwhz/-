package com.taotao.search.impl;

import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.SearchResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.search.Dao.SearchDao;
import com.taotao.search.inter.SearchItem;
import com.taotao.search.mapper.Searchmapper;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Message;
import java.util.List;

@Service
public class searchItemImpl implements SearchItem {
    @Autowired
    private Searchmapper searchmapper;
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private SearchDao searchDao;

//    @Autowired
//    private TbItemMapper tbItemMapper;
//    @Autowired
//    private JmsTemplate jmsTemplate;
//    @Resource(name = "topic")
//    private  javax.jms.Destination destination;
    @Override
    public TaotaoResult getSearchItenList() {

        try {
            List<com.taotao.pojo.SearchItem> searchList = searchmapper.getSearchList();
            for (com.taotao.pojo.SearchItem searchItem : searchList) {
                // 3、为每个商品创建一个文档对象SolrInputDocument对象。
                SolrInputDocument document = new SolrInputDocument();
                // 4、为文档添加域。必须有id域，且域的名称必须在schema.xml中定义。
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSell_point());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getCategory_name());
                document.addField("item_desc", searchItem.getItem_desc());
                // 5、把文档对象添加到索引库中。
                solrServer.add(document);
            }
            /**
             * 获取MQ的消息，取出ID更新索引库
             */
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }


    /**
     *
     * @param query 查询的主条件
     * @param page  页码
     * @param rows  显示的条数
     * @return
     * @throws Exception
     */
    @Override
    public SearchResult search(String query, Integer page, Integer rows) throws Exception {
        //1.创建SolrQuery对象
        SolrQuery query1 = new SolrQuery();
        //2.设置主查询条件+过滤条件+排序等等
        //2.1设置主查询条件
        if (query != null) {
            query1.setQuery(query);
        } else {
            query1.setQuery("*:*");
        }
        //2.2设置其他过滤条件,这里设置分页
        if(page == null ) page = 1;//page和rows为空的情况下给个默认值
        if(rows == null ) rows = 60;
        Integer start = (page -1 ) * rows;
        Integer end   = rows;
        query1.setStart(start);
        query1.setRows(end);
        //2.3设置这个条件在哪个业务域中搜索
        query1.set("df", "item_keywords");
        //2.4  设置高亮显示
        query1.setHighlight(true);//默认为false
        query1.addHighlightField("item_title");//设置要高亮的域是哪一个
        query1.setHighlightSimplePre("<font color='red'>");
        query1.setHighlightSimplePost("</font>");

        SearchResult searchResult = searchDao.search(query1);

        //3.Dao层只设置了list+总记录数，这边还要设置总页数
        long pagecount = 0;
        pagecount = (searchResult.getRecordCount()/rows);
        if((searchResult.getRecordCount()/rows)>0)
        {
            pagecount++;
        }

        searchResult.setPageCount(pagecount);
        //4.返回SearchResult

        return searchResult;
    }
}
