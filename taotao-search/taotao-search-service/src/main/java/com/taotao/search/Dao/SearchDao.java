package com.taotao.search.Dao;

import com.taotao.pojo.SearchItem;
import com.taotao.pojo.SearchResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.search.mapper.Searchmapper;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 从索引库搜索商品的dao
 */
@Repository
public class SearchDao {
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private Searchmapper searchmapper;
    /**
     * 根据查询条件从solr中查询商品的结果集
     * @param query
     * @return
     * @throws Exception
     */
    public SearchResult search(SolrQuery query) throws Exception{
        SearchResult searchResult = new SearchResult();
        //1.引入Solrsoerve，有spring进行管理。
        //2.执行查询。
        QueryResponse response = solrServer.query(query);
        //3.获取结果集，遍历结果集。
        SolrDocumentList results = response.getResults();
        searchResult.setRecordCount(results.getNumFound());//设置数据总数
        //定义一个SearchItem集合
        List<SearchItem> list = new ArrayList<>();
        /**
         * 高亮
         */
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        for(SolrDocument solrDocument : results)
        {
            //将rs中的属性 一个一个设置到SearchItem中
            //设置好的SearchItem 封住到SrarchResult中的list中。
            SearchItem searchItem = new SearchItem();
            searchItem.setId((String) solrDocument.get("id"));
            searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
            searchItem.setPrice((Long) solrDocument.get("item_price"));
            searchItem.setImage((String) solrDocument.get("item_image"));
            searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
            //item_title取高亮
            List<String> strings = highlighting.get(solrDocument.get("id")).get("item_title");
            String gaoliang ="";
            if(strings != null && strings.size()>0)
            {
                gaoliang = strings.get(0);
            }
            else
            {
                gaoliang = (String) solrDocument.get("item_title");
            }
            searchItem.setTitle(gaoliang);
            list.add(searchItem);
        }
        searchResult.setItemList(list);
        //4.设置SearchResult的属性。
        return  searchResult;
    }

    public TaotaoResult UpdateIndexById(long itemId) throws Exception
    {
        //1.注入mapper
        //2.根据id查询数据
        SearchItem searchByid = searchmapper.getSearchByid(itemId);
        //3.更新索引库,
        // 3.1创建Solrserver服务，注入进来
        //3.2创建Solrdocument对象。
        //3.3向Solrdocument对象添加域
        //3.4向索引库添加文档
        //3.5提交
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", searchByid.getId());
        document.addField("item_title", searchByid.getTitle());
        document.addField("item_sell_point", searchByid.getSell_point());
        document.addField("item_price", searchByid.getPrice());
        document.addField("item_image", searchByid.getImage());
        document.addField("item_category_name", searchByid.getCategory_name());
        document.addField("item_desc", searchByid.getItem_desc());
        solrServer.add(document);
        solrServer.commit();
        return TaotaoResult.ok();
    }
}
