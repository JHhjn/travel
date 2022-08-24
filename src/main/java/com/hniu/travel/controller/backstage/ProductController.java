package com.hniu.travel.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hniu.travel.bean.WangEditorUploadData;
import com.hniu.travel.mapper.CategoryMapper;
import com.hniu.travel.pojo.Category;
import com.hniu.travel.pojo.Product;

import com.hniu.travel.service.CategoryService;
import com.hniu.travel.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/backstage/product")
public class ProductController {
    @Autowired
    private ProductService service;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
//    @PreAuthorize("hasAnyAuthority('/product/all')")
    public ModelAndView all(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "5") Integer size){

        Page<Product> productPage = service.findPage(page, size);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("productPage",productPage);
        modelAndView.setViewName("/backstage/product_all");
        return modelAndView;
    }
    @RequestMapping("/addPage")
    public ModelAndView addPage() {
        // 查询所有产品类别
        List<Category> categoryList = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("categoryList", categoryList);
        modelAndView.setViewName("/backstage/product_add");
        return modelAndView;
    }

    @PostMapping("/add")
    public String add(Product product){
        service.addProduct(product);
        return "redirect:/backstage/product/all";
    }
    @RequestMapping("/edit")
    public ModelAndView edit(Integer pid) {
        // 查询被修改的产品
        Product product =service.findOne(pid);
        // 查询所有产品类别
        List<Category> categoryList = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product", product);
        modelAndView.addObject("categoryList", categoryList);
        modelAndView.setViewName("/backstage/product_edit");
        return modelAndView;
    }
    @PostMapping("/update")
    public String update(Product product){
        service.updateProduct(product);
        return "redirect:/backstage/product/all";
    }

    @GetMapping("/delete")
    public String delete(Integer pid,@RequestHeader("Referer") String referer){
        service.delProduct(pid);
        return "redirect:"+referer;
    }
    @RequestMapping("/upload")
    @ResponseBody
    public WangEditorUploadData upload(HttpServletRequest request, MultipartFile file) throws IOException {
        //获取项目图片存放路径
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static/upload/img/product/DescImages/";

        //判断文件夹是否存在，不存在则新建
        File file1 = new File(realPath);
        if (!file1.exists()){
            file1.mkdirs();
        }
        //获取上传文件名
        String filename = UUID.randomUUID()+file.getOriginalFilename();

       //创建一个空文件
        File newFile = new File(realPath, filename);

        //将文件写入空文件中
        file.transferTo(newFile);

        //构造返回结果
        WangEditorUploadData wangEditorUploadData = new WangEditorUploadData();
        wangEditorUploadData.setErrno(0);
        String[] data={"/upload/img/product/DescImages/"+filename};
        wangEditorUploadData.setData(data);

        return wangEditorUploadData;
    }
    @GetMapping("/updateStatus")
    public String updateStatus(Integer pid,@RequestHeader("Referer") String referer){
        service.updateStatus(pid);
        return "redirect:"+referer;
    }

}
