package com.hniu.travel.controller;

import com.hniu.travel.bean.*;
import com.hniu.travel.service.HotService;
import com.hniu.travel.service.ProductService;
import com.hniu.travel.service.ThemeService;
import com.hniu.travel.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        @Autowired
        private HotService hotService;
        @Autowired
        private ThemeService themeService;

        // 访问后台页面
        @RequestMapping("/backstage/{page}")
        public String showPageBackstage(@PathVariable String page){
            return "/backstage/"+page;
        }


        @RequestMapping("/frontdesk/index")
        public ModelAndView index(HttpSession session){
                ModelAndView modelAndView = new ModelAndView();
                PackageTour packageTour = productService.findTour();
                String cname="长沙市";
                Weather weather = weatherService.getWeatherInfo(cname);
                List<WheatherDetails> lives = weather.getLives();
                session.setAttribute("lives", lives.get(0));

              //热推
                //跟团游
                modelAndView.addObject("packageLeft",packageTour.getLeft());
                modelAndView.addObject("packageRight",packageTour.getRight());

              //境内旅游
                //热门景点
                PopularDestination hotByLevel = hotService.findHotByLevel(1);
                modelAndView.addObject("hotProduct", hotByLevel);
              //周边旅游
                //热门目的地
                TravelAround travelAround = hotService.getTravelAround(cname);
                modelAndView.addObject("travelAround",travelAround);

                //主题旅游
                //热门主题
                PopularTheme popularTheme = themeService.findPopularTheme();
                modelAndView.addObject("themeProduct", popularTheme);
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
