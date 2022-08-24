package com.hniu.travel.controller.frontdesk;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hniu.travel.pojo.Member;
import com.hniu.travel.pojo.Product;
import com.hniu.travel.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/frontdesk/favorite")
public class FavoriteController {
    @Autowired
   private ProductService productService;

    @GetMapping("/add")
    public String add(int pid, HttpSession session, @RequestHeader("Referer") String referer){
        Member member = (Member) session.getAttribute("member");
        productService.add(member.getMid(),pid);
        return "redirect:"+referer;
    }
    @GetMapping("/del")
    public String del(int pid, HttpSession session, @RequestHeader("Referer") String referer){
        Member member = (Member) session.getAttribute("member");
        productService.del(member.getMid(),pid);
        return "redirect:"+referer;
    }

    @RequestMapping("/myFavorite")
    public ModelAndView myFavorite(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "5") int size,
                                   HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Member member = (Member)session.getAttribute("member");
        Page<Product> productPage = productService.findMemberFavorite(page, size, member.getMid());
        modelAndView.addObject("productPage",productPage);
        modelAndView.setViewName("/frontdesk/my_favorite");
        return modelAndView;
    }
}
