package com.taotao.cart.impl;

import com.taotao.CartFotInter;
import com.taotao.cart.impl.jedis.JedisClient;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.CookieUtils;
import com.taotao.pojo.JsonUtils;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import io.netty.handler.codec.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 购物车接口的实现类
 */
@Service
public class CartFotInterImlp implements CartFotInter {
    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private TbItemMapper tbItemMapper;
    @Override
    public TaotaoResult addItemCart(Long userId, TbItem tbItem, Integer num) {
        //1、根据商品的ID查询商品的信息。
        //  TbItem selectByPrimaryKey = tbItemMapper.selectByPrimaryKey(tbItem.getId());
        //2、设置商品的数量和图片
        // （只需要设置图片的一张即可，数量使用num字段来表示，因为原来的num存放的是库存，所以需要重新设置一下）
        //  selectByPrimaryKey.setNum(tbItem.getNum());
        //  selectByPrimaryKey.setImage(tbItem.getImage());
        //3、根据商品id和用户id从redis数据库中查询用户的购物车的商品的列表，看是否存在（需要将JSON转成java对象）。
        TbItem queryTbItemByUserIdAndItemId = queryTbItemByUserIdAndItemId(userId, tbItem.getId());
        //3.1 如果redis中没有数据，则设置num和图片，在存入redis中
        if(queryTbItemByUserIdAndItemId == null )
        {
            System.out.println("登录的用户在redis中没有该商品数据");
            tbItem.setNum(num);//设置数量
            if(tbItem.getImage() != null)
            {
                tbItem.setImage(tbItem.getImage().split(",")[0]);
            }
            jedisClient.hset(userId+"", tbItem.getId()+"" ,JsonUtils.objectToJson(tbItem));
            jedisClient.expire(userId+"" , 864000);
            return TaotaoResult.ok();
        }
        //3.2 如果redis中有数据，则更新数量并设置回去
        else
        {
            System.out.println("登录的用户在redis中有该商品数据");
            queryTbItemByUserIdAndItemId.setNum(queryTbItemByUserIdAndItemId.getNum()+num);
            System.out.println("用户登录状态下redis中更新数量： 原先数量为： "+queryTbItemByUserIdAndItemId.getNum()+"  新数量为  "+num);
            jedisClient.hset(userId+"", tbItem.getId()+"" ,JsonUtils.objectToJson(queryTbItemByUserIdAndItemId));
            jedisClient.expire(userId+"" , 864000);
        }

        return TaotaoResult.ok();
    }

    @Override
    public TbItem queryTbItemByUserIdAndItemId(Long userId, Long itemId) {
        //根据用户id跟商品id从redis中查询是否存在
        String hget = jedisClient.hget(userId + "", itemId + "");
        TbItem jsonToPojo = JsonUtils.jsonToPojo(hget, TbItem.class);
        if(hget != null)
        {
            return jsonToPojo;
        }
        return null;
    }

    /**
     * 查询购物车列表的实现方法，分为登录状态（Redis）和未登录状态（Cookie）
     * 此处为登录状态
     * @param userId
     * @return
     */
    @Override
    public List<TbItem> getItemListByRedisOrCookie(Long userId) {
        //1.此处只做登录状态下从redis中取出的操作，cookie中没有入参，调用方法可以直接在Controlller层中操作
        //2.判断用户是否登录在Controller中判断。若是则进入if执行本方法，否则进入else执行cookie方法
        //3.通过UserId从redis中取出files集合
        Map<String, String> stringStringMap = jedisClient.hgetAll(userId + "");
        //4.设置取出的方法。entrySet方法并设置一个用与最终返回的list集合
        Set<Map.Entry<String, String>> entrySet = stringStringMap.entrySet();
        List<TbItem> list = new ArrayList<>();
        //5.遍历map集合
        for(Map.Entry<String, String>  rs : entrySet)
        {
            String value = rs.getValue();
            System.out.println("value is "+value);
            TbItem tbItem = JsonUtils.jsonToPojo(value , TbItem.class);
            System.out.println("tbitem is "+tbItem);
            list.add(tbItem);
        }
        return list;
    }

    /**
     * 更新用户登录时候的购物车数量接口的实现
     * @param userId
     * @param num
     * @return
     */
    @Override
    public TaotaoResult updItemNumByRedis(Long userId, Long itemId , Integer num) {
        //1.从redis中获取该用户所用的商品列表
        Map<String, String> stringMap = jedisClient.hgetAll(userId + "");
        Set<Map.Entry<String, String>> entrySet = stringMap.entrySet();
        //2.修改商品数量
        for(Map.Entry<String, String>  rs : entrySet)
        {
            String value = rs.getValue();
            System.out.println(value+" =============>");
            TbItem tbItem = (TbItem)JsonUtils.jsonToPojo(value , TbItem.class);
            System.out.println(tbItem+" ------------------->");
            if(tbItem.getId() == itemId.longValue())
            {
                tbItem.setNum(num);
                //3.设置回去redis中
                System.out.println(num+"*********   "+tbItem);
                jedisClient.hset(userId+"", tbItem.getId()+"" ,JsonUtils.objectToJson(tbItem));
                jedisClient.expire(userId+"" , 864000);
                break;
            }
        }
        return TaotaoResult.ok();
    }

    /**
     * 删除购物车，登录时候的删除，未登录时直接在Controller中删除cookie。
     * @param userId
     * @param itemId
     * @return
     */
    @Override
    public TaotaoResult delItemByRedis(Long userId, Long itemId) {
        TbItem tbItem = queryTbItemByUserIdAndItemId(userId, itemId);
        if(tbItem.getId() == itemId.longValue())
        {
            jedisClient.hdel(userId+"" ,itemId+"");
        }
        return TaotaoResult.ok();
    }
}
