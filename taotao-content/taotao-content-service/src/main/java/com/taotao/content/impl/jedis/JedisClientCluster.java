//package com.taotao.content.impl.jedis;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import redis.clients.jedis.JedisCluster;
//
//import java.util.Map;
//
///**
// * Redis集群版的接口实现
// */
//@Service
//public class JedisClientCluster implements JedisClient {
//    @Autowired
//    private JedisCluster jedisCluster;
//
//    @Override
//    public String set(String key, String value) {
//        return jedisCluster.set(key, value);
//    }
//
//    @Override
//    public String get(String key) {
//        return jedisCluster.get(key);
//    }
//
//    @Override
//    public Boolean exists(String key) {
//        return jedisCluster.exists(key);
//    }
//
//    @Override
//    public Long expire(String key, int seconds) {
//        return jedisCluster.expire(key, seconds);
//    }
//
//    @Override
//    public Long ttl(String key) {
//        return jedisCluster.ttl(key);
//    }
//
//    @Override
//    public Long incr(String key) {
//        return jedisCluster.incr(key);
//    }
//
//    @Override
//    public Long hset(String key, String field, String value) {
//        return jedisCluster.hset(key, field, value);
//    }
//
//    @Override
//    public String hget(String key, String field) {
//        return jedisCluster.hget(key, field);
//    }
//
//    @Override
//    public Long hdel(String key, String... field) {
//        return jedisCluster.hdel(key, field);
//    }
//
//    @Override
//    public Map<String, String> hgetAll(String key) {
//        return jedisCluster.hgetAll(key);
//    }
//}