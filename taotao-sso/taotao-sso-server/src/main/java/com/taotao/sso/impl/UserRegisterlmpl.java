package com.taotao.sso.impl;

import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.JsonUtils;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.UserRegister;
import com.taotao.sso.impl.jedis.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserRegisterlmpl implements UserRegister {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${EXPIRE_TIME.T}")
    private Integer tokenTime;

    /**
     * 校验方法的实现
     * @param param 入参值
     * @param type 类型 1 用户名  2 密码 3邮箱
     * @return
     */
    @Override
    public TaotaoResult UserCheckData(String param, Integer type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        // 2、查询条件根据传递过来的参数动态生成
        // 1、2、3分别代表username、phone、email
        if (type == 1) {
            criteria.andUsernameEqualTo(param);
        } else if (type == 2) {
            criteria.andPhoneEqualTo(param);
        } else if (type == 3) {
            criteria.andEmailEqualTo(param);
        } else {
            return TaotaoResult.build(400, "传递过来的是非法的参数");
        }
        // 执行查询
        List<TbUser> list = tbUserMapper.selectByExample(example);
        // 3、判断查询结果，如果查询到数据就返回false
        if (list == null || list.size() == 0) {
            // 4、如果没有查询到数据就返回true
            return TaotaoResult.ok(true);
        }
        // 5、使用TaotaoResult包装，并返回
        return TaotaoResult.ok(false);
    }


    /**
     * 注册方法的实现
     * @param tbUser
     * @return
     */
    @Override
    public TaotaoResult UserRegistser(TbUser tbUser) {
       // System.out.println(tbUser.getEmail().isEmpty()+"SSO-SERVER-70行");
        //1.判断是否为空
        if(tbUser.getUsername().isEmpty() || tbUser.getPassword().isEmpty())
        {
            return TaotaoResult.build(400,"用户名或者密码不能为空，请确认");
        }
        //2.校验数据
        if(!(boolean)UserCheckData(tbUser.getUsername(), 1).getData())
        {
            return TaotaoResult.build(400,"用户名已经注册");
        }
        try {
            if((tbUser.getEmail()!=null))
            {
                tbUser.setEmail(tbUser.getEmail());
            }
        } catch (Exception e) {
            System.out.println("87行有异常。。。");
        }
        if (!tbUser.getPhone().isEmpty())
        {
            tbUser.setPhone(tbUser.getPhone());
        }
        tbUser.setUsername(tbUser.getUsername());
        tbUser.setPassword(tbUser.getPassword());
        tbUser.setCreated(new Date());
        tbUser.setUpdated(tbUser.getCreated());
        //3.插入数据
        tbUserMapper.insertSelective(tbUser);
        return TaotaoResult.ok();
    }

    /**
     * 用户登录校验账户和密码是否匹配
     * @param username 用户名
     * @param password  密码
     * @return
     */
    @Override
    public TaotaoResult UserLogin(String username, String password) {
        //1.判断用户名跟密码是否为空
        if(username == null && username.length()==0 || password == null && password.length()==0)
        {
            return TaotaoResult.build(400,"用户名或者密码不能为空，请确认");
        }
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> nameList = tbUserMapper.selectByExample(example);
        criteria.andPasswordEqualTo(password);
        List<TbUser> passList = tbUserMapper.selectByExample(example);
        if(nameList.size()>0 && passList.size()>0)
        {
            //用户名跟密码校验成功，生产token；
            //生成uuid并写入redis，key是uuid，valus是session（装换成json）。
            //为了管理方便，key需要加个前缀，比如“SESSION”
            //为了安全考虑。token设置进redis的时候只要获取id就好，不应该获取密码
            String uuid = UUID.randomUUID().toString();
            TbUser user = nameList.get(0);
            user.setPassword(null);
            jedisClient.set("SsoForSession"+uuid, JsonUtils.objectToJson(user));
            System.out.println("这个是存入redis的values： "+JsonUtils.objectToJson(user));
            jedisClient.expire("SsoForSession"+uuid , tokenTime);
            return TaotaoResult.ok(uuid);

        }
        else
            return TaotaoResult.build(400 ,"用户名或者密码有误，请确认");
    }

    /**
     * 根据token从redis里面获取token的用户信息数据接口的实现类
     * @param Token
     * @return
     */
    @Override
    public TaotaoResult getUserDataByToken(String Token) {
        System.out.println(Token+"根据token从redis里面获取token的用户信息数据接口的实现类");
        String s = jedisClient.get(Token);
        TbUser ss = null;
        try {
            ss = JsonUtils.jsonToPojo(s , TbUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(ss+"根据token从redis里面获取token的用户");
        if(ss == null)
        {
            return TaotaoResult.build(200,"无此用户，请登录");
        }
        jedisClient.expire(Token,864000);
        //return TaotaoResult.ok(JsonUtils.objectToJson(s));
        return TaotaoResult.ok(ss);
    }

    /**
     * 用户退出。删除redis
     * @param Token
     */
    @Override
    public TaotaoResult UserLogout(String Token) {
        System.out.println("删除redis "+Token);
        jedisClient.expire(Token , 0);
        return TaotaoResult.ok();
    }
}
