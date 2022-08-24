package com.hniu.travel.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hniu.travel.pojo.Category;
import com.hniu.travel.pojo.Permission;
import com.hniu.travel.service.CategoryService;
import com.hniu.travel.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/backstage/category")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping("/all")
//    @PreAuthorize("hasAnyAuthority('/category/all')")
    public ModelAndView all(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer size){

        Page<Category> categoryPage = service.findPage(page, size);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("categoryPage",categoryPage);
        modelAndView.setViewName("/backstage/category_all");
        return modelAndView;
    }

    @PostMapping("/add")
    public String add(Category category){
        service.addCategory(category);
        return "redirect:/backstage/category/all";
    }
    @GetMapping("/edit")
    public ModelAndView edit(Integer cid){
        Category category = service.findCategoryById(cid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("category",category);
        modelAndView.setViewName("/backstage/category_edit");
        return modelAndView;
    }
    @PostMapping("/update")
    public String update(Category category){
        service.updateCategory(category);
        return "redirect:/backstage/category/all";
    }

    @GetMapping("/delete")
    public String delete(Integer cid){
        service.delCategory(cid);
        return "redirect:/backstage/category/all";
    }
}
