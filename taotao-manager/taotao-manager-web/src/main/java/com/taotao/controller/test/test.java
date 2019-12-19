package com.taotao.controller.test;

import com.taotao.pojo.FastDFSClientUtil;
import org.csource.fastdfs.*;

public class test {
    public static void main(String[] args) throws Exception {
//        // 0、向工程中添加jar包。目前我们使用的是maven工程，不能直接添加jar包，需要创建maven工程，安装到本地，再在使用的工程中添加依赖。
//        // 1、加载配置文件，配置文件中的内容就是tracker服务的地址。配置文件内容：tracker_server=192.168.25.133:22122
//        ClientGlobal.init("F:/IdeaProjects/taotao/taotao-manager/taotao-manager-web/src/main/resource/client.conf");
//        // 2、创建一个TrackerClient对象，我们直接new一个。
//        TrackerClient trackerClient = new TrackerClient();
//        // 3、使用TrackerClient对象创建连接，获得一个TrackerServer对象。
//        TrackerServer trackerServer = trackerClient.getConnection();
//        // 4、创建一个StorageServer的引用（不用new出来），值为null。不创建这个引用也可以，直接引用。
//        StorageServer storageServer = null;
//        // 5、创建一个StorageClient对象，该对象需要两个参数：TrackerServer对象、StorageServer的引用
//        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//        // 6、使用StorageClient对象上传图片。
//        // 扩展名不带“.”
//        String[] strings = storageClient.upload_file("D:/Backup/桌面/淘淘项目/images/bg1a.jpg", "jpg", null);
//        // 7、返回数组。包含组名和图片的路径。
//        for (String string : strings) {
//            System.out.println(string);
//        }
//    }
        // 生产环境下我们使用classpath，现在学习阶段使用全路径名，由于工具类写的不够好
        FastDFSClientUtil fastDFSClientUtils = new FastDFSClientUtil("F:/IdeaProjects/taotao/taotao-manager/taotao-manager-web/src/main/resource/client.conf");
        String string = fastDFSClientUtils.uploadFile("D:/Backup/桌面/淘淘项目/images/bg6.jpg");
        System.out.println(string);
    }
}

