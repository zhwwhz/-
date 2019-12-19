package com.taotao;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import org.springframework.http.HttpRequest;

import java.util.List;

/**
 * 购物车模块的所有接口，增删改查
 */
public interface CartFotInter {
    /**
     * 添加商品至redis购物车或者cookie购物车，考虑下为什么是taotaoresult的返回类型
     * @param userId 购物车所属的用户
     * @param tbItem 添加的商品
     * @param num 添加商品的数量
     * @return
     */
    TaotaoResult addItemCart(Long userId, TbItem tbItem, Integer num);

    /**
     * 根据用户ID和商品的ID查询购物车是否存储在redis中
     * @param userId
     * @param itemId
     * @return null 说明购物车不存在，如果不为空说明购物车存在
     */
    TbItem queryTbItemByUserIdAndItemId(Long userId, Long itemId);

    /**
     * 查询购物车列表。分为登录时候从redis中查，未登录的时候从Cookie中查
     * 注意入参是什么--是UserId
     * @param userId
     * @return
     */
    List<TbItem> getItemListByRedisOrCookie(Long userId);
    /**
     * 更新购物车列表的数量，分为登录时候从redis更新，未登录的时候从Cookie中更新（直接在Controller中实现）
     */
    TaotaoResult updItemNumByRedis(Long userId ,Long itemId , Integer num);
    /**
     * 删除购物车,分为登录时候从redis删除，未登录的时候从Cookie中删除（直接在Controller中实现）
     */
    TaotaoResult delItemByRedis(Long userId ,Long itemId);
}
