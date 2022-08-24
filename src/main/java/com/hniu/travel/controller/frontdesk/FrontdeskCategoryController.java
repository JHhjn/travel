package com.hniu.travel.controller.frontdesk;

import com.hniu.travel.pojo.Category;
import com.hniu.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/frontdesk/category")
public class FrontdeskCategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/all")
    @ResponseBody
    public List<Category> all(){
        List<Category> all = categoryService.findAll();
        return all;
    }
}
