package com.taotao.controller;

import com.taotao.content.services.TbContenList;
import com.taotao.content.services.TbcategoryList;
import com.taotao.pojo.*;
import com.taotao.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class controtest {

//    @Autowired
//    private testmapper testmapper;
    @Autowired
    private TbitemList itemList;
    @Autowired
    private ItemCatService itemCatService;
    @Autowired
    private Tbitemadd itemService;
    //内容分类管理接口
    @Autowired
    private TbcategoryList  tbcategoryList;
    //内容管理接口
    @Autowired
    private TbContenList tbContenList;
//    @Autowired
//    private PicUpload picUpload;
//    @RequestMapping("/test/findtime")
//    public String findtime()
//    {
//        return testmapper.findtime();
//    }
    @RequestMapping("/")
    public String showindex()
    {
        return "index";
    }
    @RequestMapping("/{page}")
    public String showlist(@PathVariable(value="page") String page)
    {
        return page;
    }


    @RequestMapping(value = "/item/list",method = RequestMethod.GET)
    @ResponseBody
    public EasyUIDataGridResult getlist(@RequestParam("page") int total,@RequestParam("rows") int rows)
    {
        return itemList.show(total,rows);
    }

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(@RequestParam(value="id", defaultValue="0") Long parentId) {
        // 注意：第一次请求是没有参数传过来的，我们给id一个默认值0，defaultValue="0"
        List<EasyUITreeNode> list = itemCatService.getItemCatList(parentId);
        System.out.println(parentId);
        System.out.println(list);
        return list;
    }

    /**
     * 根据商品的基础数据和商品的描述信息添加商品，返回服务器插入成功的响应状态
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping(value="/item/save", method=RequestMethod.POST)
    @ResponseBody
    public TaotaoResult savetItem(TbItem item, String desc) {

        TaotaoResult result = itemService.saveItem(item, desc);
        System.out.println(result);
        return result;
    }
    /**
     * 查询商品中的--编辑商品
     */
    @RequestMapping(value="/rest/item/update")
    @ResponseBody
    public void bianjishangping(TbItem tbItem,TbItemDesc tbItemDesc)
    {
        itemList.Upditem(tbItem,tbItemDesc);
    }
    /**
     * 查询商品中的--删除商品
     */
    @RequestMapping(value="/rest/item/delete")
    public void shanchushangping(@RequestParam("ids")Long id)
    {
        System.out.println("删除商品的id.."+id);
        itemList.DelitemById(id);
    }
    /**
     * 查询商品中的--下架商品
     */
    @RequestMapping(value="/rest/item/instock")
    public void xiajiashangping(@RequestParam("ids")Long id)
    {
        itemList.instock(id);
    }
    /**
     * 查询商品中的--上架商品
     */
    @RequestMapping(value="/rest/item/reshelf")
    public void shangjiashangping(@RequestParam("ids")Long id)
    {
        System.out.println(id);
        itemList.reshelf(id);
    }

    /***
     * 内容分类
     */
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getCategoryList(@RequestParam(value="id", defaultValue="0") Long parentId) {
        // 注意：第一次请求是没有参数传过来的，我们给id一个默认值0，defaultValue="0"
        List<EasyUITreeNode> list = tbcategoryList.showCategory(parentId);
        return list;
    }

    /**
     * 新建内容分类管理的查询
     */
    @RequestMapping(value = "/content/category/create",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult CategoryAdd(@RequestParam(value = "parentId") Long id,String name)
    {
        return tbcategoryList.createCatdgory(id,name);
    }
    /**
     * 内容管理表的查询
     */
    @RequestMapping(value = "/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult  getCategoryList(@RequestParam("categoryId") Long categoryId,@RequestParam("page") int page, @RequestParam("rows") int rows)
    {
        System.out.println("Kongzhiceng "+ categoryId+".."+page+".."+rows);
        return tbContenList.getContentList(categoryId,page,rows);
    }
    /**
     * 删除选中的内容分类管理
     */
    @RequestMapping("/content/category/delete/")
    @ResponseBody
    public void deleteCateforyById(@RequestParam("id") Long id)
    {
        tbcategoryList.deleteCategory(id);
    }

    /**
     * 更新内容管理，重命名
     * @param id
     * @param name
     * @returnpd
     */
    @RequestMapping(value = "/content/category/update",method = RequestMethod.POST)
    @ResponseBody
    public void UpdCategoryName(@RequestParam(value = "id") Long id,String name)
    {
        tbcategoryList.updateCategory(id,name);
    }
    /**
     * 内容管理--新增
     * @param content
     * @return
     */
    @RequestMapping("/content/save")
    @ResponseBody
    public TaotaoResult saveContent(TbContent content) {
        TaotaoResult result = tbContenList.SaveContent(content);
        return result;
    }
    /**
     * 内容管理--删除
     */
    @RequestMapping("/content/delete")
    public void DelContentById(@RequestParam("ids") Long id)
    {
        tbContenList.DeleContentById(id);
    }
    /**
     * 内容管理--编辑
     */
    @RequestMapping("/rest/content/edit")
    @ResponseBody
    public void UpdConetent(TbContent content)
    {
        tbContenList.UpdContent(content);
    }

    /**
     * 图片上传
     */
    @RequestMapping("/pic/upload")
    @ResponseBody
//    public PictruResult upLoad(MultipartFile uploadFile)
//    {
//        PictruResult result = picUpload.picUpload(uploadFile);
//        return result;
//    }
    public Map<String, Object> pictureUpload(MultipartFile uploadFile) {
        try {
            // 1、取出文件的扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            System.out.println(originalFilename+"  oriname is  zheg");
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            System.out.println(extName+"  extname is zheg ");
            // 2、使用工具类创建一个FastDFS的客户端
            FastDFSClientUtil fastDFSClientUtil = new FastDFSClientUtil("client.conf");
            // 3、执行上传处理，返回的字符串：group1/M00/00/01/wKgZhVjnAd6AKj_RAAvqH_kipG8211.jpg
            //F:\IdeaProjects\taotao\taotao-manager\taotao-manager-web\src\main\resource\client.conf
            String path = fastDFSClientUtil.uploadFile(uploadFile.getBytes(), extName);
            System.out.println(path+"   path is  zhe ge  ");
            // 4、拼接返回的url和ip地址，拼装成完整的url
             String url = "http://39.108.185.153/" + path;
            //String url = TAOTAO_IMAGE_SERVER_URL + path;
            // 5、返回map，设置上传成功后的图片的路径
            Map<String, Object> result = new HashMap<>();
            result.put("error", 0);
            result.put("url", url);
            // 6、返回
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            // 5、返回map，设置上传失败错误信息
            Map<String, Object> result = new HashMap<>();
            result.put("error", 1);
            result.put("message", "图片上传失败");
            return result;
        }

}
}
//client.conf    F:\IdeaProjects\taotao\taotao-manager\taotao-manager-web\src\main\resource\client.conf