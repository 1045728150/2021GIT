package com.controller;

import com.github.pagehelper.PageInfo;
import com.pojo.ProductInfo;
import com.service.ProductInfoService;
import com.util.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@RequestMapping("/prod")
@Controller
public class ProductInfoController {
    private static final int PAGE_SIZE=5;
    private String imgName="";

    @Autowired
    private ProductInfoService service;

    @RequestMapping("/getAll")
    public String showAllProduct(HttpServletRequest req){
        List<ProductInfo> list = service.selectAll();
        for(ProductInfo p:list)
        {
            System.out.println(p.getpId()+"---"+p.getpName());
        }
        req.setAttribute("list",list);
        return "product2";
    }

    /**
     * 跳转到第一页
     */
    @RequestMapping("/split")
    public String splitOnePage(HttpServletRequest req){
        PageInfo pageInfo= service.splitPage(1,PAGE_SIZE);
        req.setAttribute("info",pageInfo);
        return "product";
    }

    /**
     *处理点击页面导航栏的ajax跳页请求
     */
    @ResponseBody
    @RequestMapping("/splitByPage")
    public void splitByPage(Integer page, HttpServletRequest req, HttpSession session){
        PageInfo pageInfo = service.splitPage(page, PAGE_SIZE);
        req.removeAttribute("info");
        session.setAttribute("info",pageInfo);
        //System.out.println("请求第"+pageInfo.getPageNum()+"页");
    }

    @ResponseBody
    @RequestMapping("/ajaxImg")
    public String ajaxImg(MultipartFile pimage,HttpServletRequest req)  {
        imgName = FileNameUtil.getUUIDFileName() +
                FileNameUtil.getFileType(pimage.getOriginalFilename());  //①获取新UUID文件名
        System.out.println("图片路径："+imgName);
        String basePath = req.getServletContext().getRealPath("/image_big");
        String imgPath = basePath + File.separator + imgName;   //②拼接出图片最终存放发路径（默认存放到/image_big中）
        System.out.println("图片存放路径："+imgPath);
        try {
            pimage.transferTo(new File(imgPath));      //③使用.transferTo方法，把图片存放到指定位置
        } catch (IOException e) {
            e.printStackTrace();
        }
//        JSONObject object=new JSONObject();
//        object.put("imgUrl",imgName);
//        return object.toString();
        return imgName;
    }

    @RequestMapping("save")
    public String addProduct(ProductInfo productInfo,HttpServletRequest req){
          productInfo.setpImage(imgName);
          productInfo.setpDate(new Date());
          int num=-1;
        try {
            num=service.addProduct(productInfo);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if(num>0)
            req.setAttribute("msg","添加成功");
        else
            req.setAttribute("msg","添加失败");
        imgName="";   //清空，不影响下一次使用
        return  "forward:/prod/split.action";  //跳转到分页显示的第一页
    }

    @RequestMapping("/one")
    public String EditProduct(Integer pid,Integer page,HttpServletRequest req){
        ProductInfo productInfo = service.selectById(pid);
        req.setAttribute("prod",productInfo);
        req.setAttribute("page",page);
        return "update";
    }

    /**
     * 跳转到特定页面
     */
    @RequestMapping("/goToPage")
    public String splitOnePage(Integer page,HttpServletRequest req){
        PageInfo pageInfo= service.splitPage(page,PAGE_SIZE);
        req.setAttribute("info",pageInfo);
        return "product";
    }

    @RequestMapping("/update")
    public String updateProduct(HttpServletRequest req,ProductInfo productInfo,Integer page){
        System.out.println("当前页为："+page);
        if(!imgName.equals("")){
            productInfo.setpImage(imgName);
        }
        int count=-1;
        try {
            count=service.updateProduct(productInfo);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if(count==1)
            req.setAttribute("msg","修改成功");
        else
            req.setAttribute("msg","修改失败");
        imgName="";
        return "forward:/prod/goToPage.action?page="+page;           //跳到分页处理页面
    }

    @ResponseBody
    @RequestMapping(value="/delete",produces = "text/html;charset=UTF-8")
    public String deleteOne(Integer pid,HttpServletRequest req,Integer page){
        String msg="";
        String imgPath="";
        int count=-1;
        try {
            ProductInfo productInfo = service.selectById(pid);
            String basePath = req.getServletContext().getRealPath("/image_big");
            imgPath = basePath + File.separator + productInfo.getpImage();
            System.out.println("imgPath="+imgPath);
            count=service.deleteOne(pid);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if(count==1){
            File file=new File(imgPath);
            if(file.exists())
                file.delete();
            msg="删除商品成功😊";
        }else{
           msg="对不起，删除商品失败!";
        }
        PageInfo info=service.splitPage(page,PAGE_SIZE);
        req.getSession().setAttribute("info",info);
        return msg;
    }

    @ResponseBody
    @RequestMapping(value="/deletebatch",produces = "text/html;charset=UTF-8")
    public String deleteBatch(String str,HttpServletRequest req){
        String msg="";
        int num=-1;
        String []ids=str.split(",");
        try {
            num=service.deleteBatch(ids);
            if(num>0){
                msg="批量删除成功";
            }else{
                msg="批量删除失败";
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            msg="当前存在商品不可删除！";
        }
        PageInfo info=service.splitPage(1,PAGE_SIZE);
        req.getSession().setAttribute("info",info);  //因为使用了$("#table").load方法，如果使用req则无法刷新数据
        return msg;

    }
}
