package com.taotao.itemweb.pojo;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.taotao.pojo.TbItem;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Items添加一个images的属性
 */
public class itemweb extends TbItem {
    public itemweb(TbItem item)
    {

        org.springframework.beans.BeanUtils.copyProperties(item,this);

    }
    public String[] getImages()
    {
        if(super.getImage() != null)
        {
            return super.getImage().split(",");

        }
        return  null;
    }
}
