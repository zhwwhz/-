package com.taotao.content.impl.test;

import com.taotao.content.impl.jedis.JedisClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class redisTestByXML {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        JedisClient jedisClient = context.getBean(JedisClient.class);
        jedisClient.set("testdanijban","asd156a4sd6a1s65da");
        System.out.println(jedisClient.get("testdanijban"));
    }
}
