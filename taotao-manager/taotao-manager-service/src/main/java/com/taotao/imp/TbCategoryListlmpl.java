//package com.taotao.imp;
//
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.taotao.mapper.TbContentMapper;
//import com.taotao.pojo.*;
//import com.taotao.services.TbCategoryList1;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * 内容管理的实现类
// */
//@Service
//public class TbCategoryListlmpl implements TbCategoryList1 {
//    @Autowired
//    private TbContentMapper tbContentMapper;//内容表的接口
//    @Override
//    public EasyUICategoryResult FindCategoryListByid(Long categoryId, Integer total, Integer rows) {
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
//        List<TbContent>  list = tbContentMapper.selectByExample(example);
//        System.out.println(list);
//        //4.获取数据
//        PageInfo<TbContent> info = new PageInfo<>(list);
//        //5.封装到自定义的json包装类 EasyUICategoryResult
//        EasyUICategoryResult result = new EasyUICategoryResult();
//        result.setRows(info.getList());
//        result.setTotal(info.getTotal());
//        return result;
//    }
//}
