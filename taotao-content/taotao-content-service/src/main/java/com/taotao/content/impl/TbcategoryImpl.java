package com.taotao.content.impl;

import com.taotao.content.services.TbcategoryList;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.java2d.pipe.SpanIterator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class TbcategoryImpl implements TbcategoryList {
   @Autowired
   private TbContentCategoryMapper tbContentCategoryMapper;
    @Override
    public List<EasyUITreeNode> showCategory(Long id) {
       //1.引入接口层的引用
       //2.创建example
        TbContentCategoryExample example = new TbContentCategoryExample();
        //3.设置查询条件
        TbContentCategoryExample.Criteria criteria =example.createCriteria();
        criteria.andParentIdEqualTo(id);
        //4.执行查询
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        //5.转换成EasyUITreeNode,先要有个list的EasyUITreeNode
        List<EasyUITreeNode> nodes = new ArrayList<>();
        for(TbContentCategory ls : list)
        {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(ls.getId());
            node.setState(ls.getIsParent()?"closed":"open");
            node.setText(ls.getName());
            nodes.add(node);
        }
        //6.返回
        return nodes;
    }

    /**
     * 新建分类
     * @param id
     * @param name
     * @return
     */
    @Override
    public TaotaoResult createCatdgory(Long id, String name) {
        System.out.println(id);
        System.out.println(name);
        //补全其他属性
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setIsParent(false);
        tbContentCategory.setParentId(id);
        tbContentCategory.setName(name);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setStatus(1);
        tbContentCategory.setUpdated(tbContentCategory.getCreated());

        //插入到表中
        tbContentCategoryMapper.insertSelective(tbContentCategory);
        //如果更新的节点原本是叶子节点的话，要修改成父节点
        TbContentCategory tb = tbContentCategoryMapper.selectByPrimaryKey(id);
           if (tb.getIsParent() == false) {
               tb.setIsParent(true);
               tbContentCategoryMapper.updateByPrimaryKeySelective(tb);
           }
        return TaotaoResult.ok(tbContentCategory);
    }

    /**
     * 删除分类
     * @param id
     */
    @Override
    public void deleteCategory(Long id) {
        tbContentCategoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新分类，重命名，其他属性不变
     * @param id
     * @param name
     * @return
     */
    @Override
    public void updateCategory(Long id, String name) {
        System.out.println(id+name);
        TbContentCategory tb = tbContentCategoryMapper.selectByPrimaryKey(id);
        System.out.println(tb);
        tb.setName(name);
        tbContentCategoryMapper.updateByPrimaryKeySelective(tb);
    }
}
