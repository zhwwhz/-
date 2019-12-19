package com.taotao.sso;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

/**
 * 用户注册的接口，思考返回值类型是什么？
 */
public interface UserRegister {
    /**
     * 根据参数校验用户登录的数据是否可用
     * @param param 入参值
     * @param type 类型 1 用户名  2 密码 3邮箱
     * @return
     */
    public TaotaoResult UserCheckData(String param , Integer type);

    /**
     * 根据用户传入的信息注册客户信息
     * @param tbUser
     * @return
     */
    public TaotaoResult UserRegistser(TbUser tbUser);

    /**
     * 用户登录，校验用户的账户跟密码
     * @param username
     * @param password
     * @return
     */
    public TaotaoResult UserLogin(String username , String password);
    /**
     * 根据token从redis里面获取token的用户信息数据。
     */
    public TaotaoResult getUserDataByToken(String Token);

    /**
     * 用户退出，删除redis
     * @param Token
     */
    public TaotaoResult UserLogout(String Token);
}
