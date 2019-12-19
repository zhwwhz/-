package com.taotao.imp;

import com.taotao.pojo.FastDFSClientUtil;
import com.taotao.pojo.PictruResult;
import com.taotao.services.PicUpload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
@Service
public class PicUploadlpml implements PicUpload {
    @Override
    public PictruResult picUpload(MultipartFile multipartFile) {
        PictruResult result = new PictruResult();
        //1.判断为空的情况
        if(multipartFile.isEmpty())
        {
            result.setError(1);
            result.setMessage("图片上传失败，图片不存在");
            return result;
        }
        try {
//            FastDFSClientUtil clientUtil = new FastDFSClientUtil("classpath:F:/IdeaProjects/taotao/taotao-manager/taotao-manager-web/src/main/resource/client.conf");
            // 1、取出文件的扩展名
            String originalFilename = multipartFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            // 2、使用工具类创建一个FastDFS的客户端
            FastDFSClientUtil fastDFSClientUtil = new FastDFSClientUtil("classpath:F:/IdeaProjects/taotao/taotao-manager/taotao-manager-web/src/main/resource/client.conf");
            // 3、执行上传处理，返回的字符串：group1/M00/00/01/wKgZhVjnAd6AKj_RAAvqH_kipG8211.jpg
            String path = fastDFSClientUtil.uploadFile(multipartFile.getBytes(), extName);
            // 4、拼接返回的url和ip地址，拼装成完整的url
             String url = "http://192.168.25.135/" + path;
            System.out.println(url+"...di34航...");
            //String url = TAOTAO_IMAGE_SERVER_URL + path;
            // 5、返回map，设置上传成功后的图片的路径
            result.setError(1);
            result.setUrl(url);
            // 6、返回
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // 5、返回map，设置上传失败错误信息
            return result;
        }
    }
}
