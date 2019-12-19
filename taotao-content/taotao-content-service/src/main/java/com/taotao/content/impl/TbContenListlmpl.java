package com.taotao.content.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.content.impl.jedis.JedisClient;
import com.taotao.content.services.TbContenList;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内容管理表接口的实现类
 */
@Service
public class TbContenListlmpl implements TbContenList {
    @Autowired
    private TbContentMapper tbContentMapper;//内容表的接口
    @Autowired
    private JedisClient jedisClient;
//    @Override
//    public EasyUICategoryResult getContentList(Long categoryId, Integer total, Integer rows) {
////       if(categoryId == 0)
////       {
////           categoryId =1;
////       }
//        System.out.println(categoryId);
//        if(total == 0)
//        {
//            total=1;
//        }
//
//        if(rows==0)
//        {
//            rows=20;
//        }
//        //1.获取分页插件并设置
//        PageHelper.startPage(total,rows);
//        //2.创建查询条件实例（TbContentExample对象）
//        TbContentExample example = new TbContentExample();
//        TbContentExample.Criteria criteria = example.createCriteria();
//        criteria.andCategoryIdEqualTo(categoryId);
//        //3.注入mapper对象，调用mapper对象的接口方法
//        List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
//        System.out.println(list);
//        //4.获取数据
//        PageInfo<TbContent> info = new PageInfo<>(list);
//        //5.封装到自定义的json包装类 EasyUICategoryResult
//        EasyUICategoryResult result = new EasyUICategoryResult();
//        result.setRows(list);
//        result.setTotal(info.getTotal());
//        return result;
//    }

    /**
     * 参考的方法
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
        System.out.println(categoryId+"64行..");
        if(page == null)
        {
            page = 1;
        }

        if(rows == null)
        {
            rows = 20;
        }
        //1.获取分页插件并设置
        PageHelper.startPage(page , rows);
        //2.创建查询条件实例（TbContentExample对象）
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        Long a =(long)categoryId;
        criteria.andCategoryIdEqualTo(a);
        //3.注入mapper对象，调用mapper对象的接口方法
        System.out.println("测试有没有执行到这一步...82行...");
        List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
        System.out.println("测试有没有执行到这一步...84行...");
        //4.获取数据
        PageInfo<TbContent> info = new PageInfo<>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(info.getTotal()); result.setRows(list);
        System.out.println(result+"   result00  ");
        return result;
    }
    /**
     * 内容管理-新增
     */
    @Override
    public TaotaoResult SaveContent(TbContent content) {
        TbContent tb = new TbContent();
        tb.setCategoryId(content.getCategoryId());
        tb.setContent(content.getContent());
        tb.setCreated(content.getCreated());
        tb.setPic(content.getPic());
        tb.setPic2(content.getPic2());
        tb.setTitle(content.getTitle());
        tb.setSubTitle(content.getSubTitle());
        tb.setUrl(content.getUrl());
        tb.setTitleDesc(content.getTitleDesc());
        tbContentMapper.insertSelective(tb);
        //新增時候同步緩存--刪除緩存，下次執行時候就會重新從數據庫取出並寫入最新的數據。
        try {
            System.out.println("這裡表示新增的時候刪除緩存啦。。。~~~");
            jedisClient.hdel("Content",content.getCategoryId()+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }
    /**
     * 内容管理-删除
     */
    @Override
    public void DeleContentById(Long id) {
        System.out.println(id + "...删除的id...");
        try {
//            TbContent tbContent = tbContentMapper.selectByPrimaryKey(id);
            System.out.println("這裡表示删除的時候刪除緩存啦。。。~~~");
            jedisClient.hdel("Content",tbContentMapper.selectByPrimaryKey(id).getCategoryId()+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        tbContentMapper.deleteByPrimaryKey(id);
    }
    /**
     * 内容管理-编辑
     */
    @Override
    public void UpdContent(TbContent tbContent)
    {
        System.out.println(tbContent + "...编辑好的内容...");
        try {
            System.out.println("這裡表示删除的時候刪除緩存啦。。。~~~");
            jedisClient.hdel("Content",tbContent.getCategoryId()+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        tbContentMapper.updateByPrimaryKeySelective(tbContent);
    }
    /**
     * 首页大广告位置的轮播图接口的实现
     * 1.自动注入接口
     * 2.设置查询条件
     * 3.需要添加缓存，使用缓存的一个先决条件是：不允许原有业务逻辑，所以要捕获异常（Try-Catch）
     */
    @Override
    public List<TbContent> getContentByCategoryId(Long categoryId) {
            //先判断数据在缓存中是否已经存在，
            // 若存在直接从redis中取，不存在则从数据库取出并写入缓存中。
        //這裡表示緩存中有數據，不需要從數據庫中取出了
         String str= jedisClient.hget("Content",categoryId+"");
         if(str!=null)

             try {
                 System.out.println("這裡表示有緩存啦，直接從緩存中取數據了。");
                 return JsonUtils.jsonToList(str,TbContent.class);
             } catch (Exception e) {
                 e.printStackTrace();
             }


            //    TbContentExample example = new TbContentExample();
            //    TbContentExample.Criteria criteria = example.createCriteria();
            //        criteria.andCategoryIdEqualTo(categoryId);
            //                List<TbContent> ls = tbContentMapper.selectByExample(example);
            //        return ls;

            //以下可以合并成一句话
            //new TbContentExample().createCriteria().andCategoryIdEqualTo(categoryId) 等价于 example
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = tbContentMapper.selectByExample(example);

        //2.從數據庫取出後寫入緩存，使用的是hset形式。
        try {
            System.out.println("這裡表示沒有緩存，從數據庫取出後寫入緩存中。。。");
            jedisClient.hset("Content",categoryId+"",JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
















