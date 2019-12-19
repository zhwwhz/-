package com.taotao.content.impl.test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import javax.xml.transform.Source;
import java.util.HashSet;
import java.util.Set;

/**
 * 测试redis的客户端使用
 */
public class redieTest {
    public static void main(String[] args) {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("39.108.185.153",6379));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("cluseter","cluster");
        System.out.println(jedisCluster.get("cluseter"));
        jedisCluster.close();
    }
}
