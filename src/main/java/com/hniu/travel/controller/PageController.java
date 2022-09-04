package com.hniu.travel.controller;

import com.hniu.travel.bean.PackageTour;
import com.hniu.travel.bean.Weather;
import com.hniu.travel.bean.WheatherDetails;
import com.hniu.travel.service.ProductService;
import com.hniu.travel.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PageController {
        @Autowired
        private ProductService productService;
        @Autowired
        private WeatherService weatherService;

        // 访问后台页面
        @RequestMapping("/backstage/{page}")
        public String showPageBackstage(@PathVariable String page){
            return "/backstage/"+page;
        }


        @RequestMapping("/frontdesk/index")
        public ModelAndView index(HttpSession session){
                ModelAndView modelAndView = new ModelAndView();
                PackageTour packageTour = productService.findTour();
                Weather weather = weatherService.getWeatherInfo("长沙市");
                List<WheatherDetails> lives = weather.getLives();
                session.setAttribute("lives", lives.get(0));
                modelAndView.addObject("packageLeft",packageTour.getLeft());
                modelAndView.addObject("packageRight",packageTour.getRight());
                modelAndView.setViewName("/frontdesk/index");
                return modelAndView;
        }

        // 访问前台页面
        @RequestMapping("/frontdesk/{page}")
        public String showPageFrontdesk(@PathVariable String page){
            return "/frontdesk/"+page;
        }

        // 忽略访问项目logo
        @RequestMapping("favicon.ico")
        @ResponseBody
        void returnNoFavicon() {}

}
