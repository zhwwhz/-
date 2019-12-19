package com.taotao.services;

import com.taotao.pojo.PictruResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传的接口
 */
public interface PicUpload {
    /**
     * 接口
     */
    PictruResult picUpload(MultipartFile multipartFile);
}
