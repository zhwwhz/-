package com.taotao.cart.impl.jedis;
import java.util.Map;

/**
 * redie的客户端的接口，通过在xml配置文件中切换实现类，bean的注入来控制单机还是集群。
 */
public interface JedisClient {

    String set(String key, String value);

    String get(String key);

    Boolean exists(String key);

    Long expire(String key, int seconds);

    Long ttl(String key);

    Long incr(String key);

    Long hset(String key, String field, String value);

    String hget(String key, String field);

    Long hdel(String key, String... field); // 删除hkey

    Map<String, String> hgetAll(String key); // 查询所有的hash类型的列表
}
