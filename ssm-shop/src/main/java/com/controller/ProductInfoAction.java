package com.controller;

import com.github.pagehelper.PageInfo;
import com.pojo.ProductInfo;
import com.pojo.vo.ProductInfoVo;
import com.service.ProductInfoService;
import com.service.impl.ProductInfoServiceImpl;
import com.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    @Autowired
    private ProductInfoService service;

    //上传图片的名称
    String savefileName="";
    //每页显示的记录数
    public static final int PAGE_SIZE=5;

    //显示全部商品不分页
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest req){
        List<ProductInfo> list = service.getAll();
        req.setAttribute("list",list);
        return "product";
    }

    //显示某一页的5条记录(默认显示第一页数据，页数包含在prodVo中)
    @RequestMapping("/split")
    public String split(HttpServletRequest req){
        PageInfo pageInfo=null;
        Object vo=req.getSession().getAttribute("prodVo");   //取出查询条件
        //vo!=null，表明现在要进行带条件查询,移除prodVo；如果为空，那么跳转到第一页
        if(vo!=null){
            pageInfo = service.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
            req.getSession().removeAttribute("prodVo");
        }else{
            pageInfo = service.splitPage(1, PAGE_SIZE);
        }
        req.setAttribute("info",pageInfo);
        return "product";
    }
    /*
      ajax分页的翻页处理：
               显示某一页的5条记录(配合ajaxsplit请求,当前页码page已经被ajax放入request作用域中)
      该方法只需要更新info中存放的pageinfo即可，无需返回值,把更新后的info放入session
     */
    @ResponseBody
    @RequestMapping("/ajaxSplit")
    public void ajaxSplit(ProductInfoVo vo,HttpSession session,HttpServletRequest req){
        System.out.println("-------"+vo+"-------");
        //取得当前vo对象的数据，封装到pageInfo中
        PageInfo pageInfo = service.splitPageVo(vo,PAGE_SIZE);
        req.removeAttribute("info");
        session.setAttribute("info",pageInfo);
    }

    /**
     * 异步ajax的文件上传处理
     *     参数：MultipartFile类型，专门用来进行上传的文件流对象的接收
     *          形参名称==在上传页面所编写的页面提交的文件name属性名称
     *     返回ajax对象
     */
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest req){
        //1.提取生成文件名UUID+上传图片的后缀
        savefileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());
        //2.得到项目中图片存储的路径
        String path=req.getServletContext().getRealPath("/image_big");
        System.out.println("添加图片的路径为："+path+ File.separator+savefileName);
        //3.把1中名称的图片转存到2中的路径
        // 路径path+反斜杠+文件名   File.separator 的作用相当于 ' \  '
        try {
            pimage.transferTo(new File(path+ File.separator+savefileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //4.实现图片回显
        JSONObject object=new JSONObject();
        object.put("imgurl",savefileName);  //把当前上传的图片名称转为jsonObject，名字叫imgurl
        return object.toString();     //返回json格式对象：需要JsonObject.toString
    }

    /**
     * 增加商品功能
     * @return
     */
    @RequestMapping("/save")
    public String save(ProductInfo productInfo,HttpServletRequest req){
        //除了时间和图片名称以外的数据，已经由框架根据 请求参数名=对象属性名 方式赋值
        productInfo.setpDate(new Date());
        productInfo.setpImage(savefileName);
        int num=-1;
        try{
            num=service.save(productInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(num>0){
            req.setAttribute("msg","增加商品成功");
        }else{
            req.setAttribute("msg","增加失败");
        }
        //清空saveFileName变量中的内容，为了不影响下一次增加或修改的异步ajax
        savefileName="";
        //增加成功后重新访问数据库，跳转到分页显示的action
        return "forward:/prod/split.action";
    }

    /**
     * 根据主键id查找商品，要包括商品条件
     */
    @RequestMapping("/one")
    public String one(int pid,ProductInfoVo vo, Model model,HttpSession session ){
        ProductInfo productInfo = service.getByID(pid);
        //把查询条件和当前页码封装到session中，叫做prod
        session.setAttribute("prodVo",vo);
        //把查询到的商品列表封装到req中，叫做prod
        model.addAttribute("prod",productInfo);
        return "update"; //返回商品更新页面
    }

    /**
     * 更新商品信息
     */
    @RequestMapping("/update")
    public String update(ProductInfo productInfo,HttpServletRequest req){
        if(!savefileName.equals(""))
            productInfo.setpImage(savefileName);   //如果本次上传新图片，则使用新图片名称(隐藏表单域给出)
                                                   //如果没有上传新图片，则在隐藏表单域中，Image被自动赋值为原来的值
        int count=-1;
        try {
            count = service.updateProductInfo(productInfo);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(count>0){
            req.setAttribute("msg","商品更新成功");
        }else{
            req.setAttribute("msg","更新失败");
        }
        savefileName="";
        return "forward:/prod/split.action";
    }

    /**
     * 删除单个商品
     *   此处不需要responceBody，因为执行完ajax功能后，要调到分页的页面执行最后的跳转处理
     */
    @ResponseBody
    @RequestMapping(value="/delete",produces = "text/html;charset=UTF-8")
    public Object deleteOne(int pid,HttpServletRequest req,ProductInfoVo vo){
        int count=-1;
        try {
            count = service.deleteOne(pid);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if(count>0){
            //当删除成功后，将vo中保存的条件查询语句放入session的deleteProdVo属性中
            req.setAttribute("msg","删除成功");
        }else {
            req.setAttribute("msg","删除失败");
        }
        PageInfo info=null;
        if(vo!=null){
            //vo不为空，则带条件查询
            info=service.splitPageVo(vo,PAGE_SIZE);
        }else{
            //没有条件，不带条件查询，返回第一页
            info=service.splitPage(1,PAGE_SIZE);
        }
       req.getSession().setAttribute("info",info);  //因为使用了$("#table").load方法，如果使用req则无法刷新数据
       return req.getAttribute("msg");
    }

    /**
     * 批量删除商品
     * pids字符串由多个id拼接而成，比如 pids="1,4,5"
     */
    @ResponseBody
    @RequestMapping(value="/deleteBatch",produces = "text/html;charset=UTF-8")
    public Object deleteBatch(String pids,HttpServletRequest req,ProductInfoVo vo){
        //将上传上来的字符串，处理后形参商品id的字符数组
        String ps[]=pids.split(",");  //以逗号分割pids字符串，形成数组ps=[1,4,5]

        try {
            int num= service.deleteBatch(ps);
            if(num>0){
                req.setAttribute("msg","批量删除成功");
            }else{
                req.setAttribute("msg","批量删除失败");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            req.setAttribute("msg","当前存在商品不可删除！");
        }
        PageInfo info=null;
        if(vo!=null){
            //vo不为空，则带条件查询
            info=service.splitPageVo(vo,PAGE_SIZE);
        }else{
            //没有条件，不带条件查询，返回第一页
            info=service.splitPage(1,PAGE_SIZE);
        }
        return req.getAttribute("msg");
    }

    /**
     * 多条件查询商品:ajax异步查询---不分页
     */
    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo,HttpSession session,HttpServletRequest req){
        List<ProductInfo> infoList=service.selectCondition(vo);
        session.setAttribute("list",infoList);
    }
}
