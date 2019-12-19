package com.taotao.content.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class text {
    public static void main(String[] args) {
        // 1、先在Mybatis的配置文件中配置拦截器插件：作用是在执行sql语句之前，将sql语句拦截下来，在sql语句中拼接上limit

        // 2、在执行查询之前设置分页条件，使用Pagehelper的静态方法
        PageHelper.startPage(1, 3);

        // 3、执行查询，使用mapper，需要初始化一个spring容器，从spring容器中的得到这个mapper的代理对象
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-dao.xml");
        TbContentMapper itemMapper = applicationContext.getBean(TbContentMapper.class);
        // 创建TbItemExample对象
        TbContentExample example = new TbContentExample();//这个对象是设置查询条件所用
        // 调用createCriteria()方法，创建条件查询对象，本测试没有条件，可以不用调用，表示查询所有
        TbContentExample.Criteria criteria = example.createCriteria();
        Long a =(long)89;
        criteria.andCategoryIdEqualTo(a);
        List<TbContent> list = itemMapper.selectByExample(example); // 本测试没有条件，表示查询所有

        // 4、取出分页信息，分页后，实际返回的结果list类型是Page<E>，如果想取出分页信息
        // 方式一：需要强制转换为Page<E>
        // Page<TbItem> listIteam = (Page<TbItem>)list;
        // 方式二：我们使用PageInfo对象对查询出来的结果进行包装，推荐使用第二种
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        pageInfo.getPrePage();
        // PageInfo中包含了非常全面的分页属性，下面演示几个
        System.out.println("总记录数：" + pageInfo.getTotal());
        System.out.println("总页数：" + pageInfo.getPages());
        System.out.println("当前页数：" + pageInfo.getPageNum());
        System.out.println("每页记录数：" + pageInfo.getPageSize());
        for(TbContent it : pageInfo.getList())
        {
            System.out.println(it.toString());
        }
    }

}
