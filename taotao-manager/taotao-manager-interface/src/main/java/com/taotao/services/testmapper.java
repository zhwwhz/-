package com.taotao.services;

public interface testmapper {
     String findtime();
}

//    <!-- 引用dubbo服务 ：需要先引入dubbo的约束-->
//    <dubbo:application name="taotao-manager-web"/>
//    <dubbo:registry protocol="zookeeper" address="192.168.5.130:2181"/>
//    <dubbo:reference interface="com.taotao.service.ItemService" id="itemService" />


//    <!-- 提供方应用信息，用于计算依赖关系 -->
//    <dubbo:application name="taotao-manager" />
//    <dubbo:registry protocol="zookeeper" address="192.168.5.130:2181" />
//    <!-- 用dubbo协议在20880端口暴露服务 -->
//    <dubbo:protocol name="dubbo" port="20880" />
//    <!-- 声明需要暴露的服务接口 -->
//    <dubbo:service interface="com.taotao.service.ItemService" ref="itemServiceImpl" />