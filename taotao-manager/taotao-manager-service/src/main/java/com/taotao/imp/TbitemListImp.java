package com.taotao.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manager.impl.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.*;
import com.taotao.services.TbitemList;
import com.taotao.services.Tbitemadd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("tbitemListImp")
public class TbitemListImp implements TbitemList {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${EXPIRE_TIME.T}")
    private Integer EXPIRE_TIME1;

    /**
     * 商品查询
     * @param total
     * @param rows
     * @return
     */
    @Override
    public EasyUIDataGridResult show(Integer total,Integer rows) {
        if(total == 0)
        {
            total=1;
        }

        if(rows==0)
        {
            rows=30;
        }
        //1.获取分页插件并设置
        PageHelper.startPage(total,rows);
        //2.创建查询条件实例（TbItemExample对象）
        TbItemExample example = new TbItemExample();
        //3.注入mapper对象，调用mapper对象的接口方法
        List<TbItem>  list= tbItemMapper.selectByExample(example);
       //4.获取数据
        PageInfo<TbItem> info = new PageInfo<>(list);
        //5.封装到自定义的json包装类 EasyUIDataGridResult
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(info.getTotal());
        result.setRows(info.getList());
        return result;
    }
    /**
     * 根据id删除商品
     */
    @Override
    public void DelitemById(Long id) {
        tbItemMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据id下架商品
     */
    @Override
    public void instock(Long id) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        tbItem.setStatus((byte)2);
        tbItemMapper.updateByPrimaryKeySelective(tbItem);
    }

    /**
     * 根据id上架商品
     */
    @Override
    public void reshelf(Long id) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        tbItem.setStatus((byte)1);
        tbItemMapper.updateByPrimaryKeySelective(tbItem);
    }

    /**
     * 编辑商品
     */
    @Override
    public void Upditem(TbItem item, TbItemDesc tbItemDesc) {
        tbItemMapper.updateByPrimaryKey(item);
        Date date = new Date();
        tbItemDesc.setItemDesc(tbItemDesc.getItemDesc());
        tbItemDesc.setUpdated(date);
        tbItemDescMapper.updateByPrimaryKey(tbItemDesc);
    }
    /**
     * 获取商品详情页面的商品信息的接口，解决访问量大的情况--添加缓存
     */
    @Override
    public TbItem getItemMoreById(Long itemId) {
        /**
         * 业务逻辑，有的话从缓存查，没有的话从数据库查并写入缓存中
         * 这里指的关注的是redis存储的key的取值。
         * 有讲究--为什么用Stringf？因为过期时间的设置不要用在File中、排除Hset
         *       --如何设置key，使得便于管理。用冒号分割，类似包名的形式。
         */

        String s = jedisClient.get("Item_Info:" + itemId + ":BASE");

            if(s != null && s.length()>0 )
            {
                try {
                    jedisClient.expire("Item_Info:"+itemId+":BASE",EXPIRE_TIME1);//设置过期时间
                    System.out.println("这里代表详情页面信息在缓存中有了。从缓存中取出");
                    return JsonUtils.jsonToPojo(s,TbItem.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);//没有在缓存中查到，从数据库取出

        try {
            jedisClient.set("Item_Info:"+itemId+":BASE", JsonUtils.objectToJson(tbItem));//存入缓存中
            jedisClient.expire("Item_Info:"+itemId+":BASE",EXPIRE_TIME1);//设置过期时间
            System.out.println("这里代表详情页面信息在缓存中没有。写入缓存中");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tbItem;
    }

    /**
     * 获取商品详情页面的商品描述的接口，解决访问量大的情况--添加缓存
     * @param itemId
     * @return
     */
    @Override
    public TbItemDesc getItemDescById(Long itemId) {

        String s = jedisClient.get("Item_Info:" + itemId + ":DESC");

        if(s != null && s.length()>0 )
        {
            try {
                jedisClient.expire("Item_Info:"+itemId+":DESC",EXPIRE_TIME1);//设置过期时间
                System.out.println("这里代表详情页面信息在缓存中有了。从缓存中取出");
                return JsonUtils.jsonToPojo(s,TbItemDesc.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);

        try {
            jedisClient.set("Item_Info:"+itemId+":DESC", JsonUtils.objectToJson(tbItemDesc));//存入缓存中
            jedisClient.expire("Item_Info:"+itemId+":DESC",EXPIRE_TIME1);//设置过期时间
            System.out.println("这里代表详情页面信息在缓存中没有。写入缓存中");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbItemDesc;
    }
}
