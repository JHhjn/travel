package com.hniu.travel.controller.frontdesk;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hniu.travel.bean.PackageTour;
import com.hniu.travel.pojo.Member;
import com.hniu.travel.pojo.Product;
import com.hniu.travel.service.HotService;
import com.hniu.travel.service.ProductService;
import com.hniu.travel.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/frontdesk/product")
public class FrontdeskProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private HotService hotService;

    /**
     * 查询旅游线路
     *
     * @param cid     线路类别id
     * @param productName 线路名
     * @param page     页数
     * @param size     每页条数
     * @return
     */
    @RequestMapping("/routeList")
    public ModelAndView findProduct(Integer cid,
                                    String productName,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer size) {
        ModelAndView modelAndView = new ModelAndView();
        Page<Product> productPage = productService.findProduct(cid, productName, page, size);
        modelAndView.addObject("productPage", productPage);
        modelAndView.addObject("cid", cid);
        modelAndView.addObject("productName", productName);
        //右侧边栏的热门推荐景点
        List<Product> rightHot = hotService.findRightHot();
        modelAndView.addObject("rightHot",rightHot);
        modelAndView.setViewName("/frontdesk/route_list");
        return modelAndView;
    }

    // 线路详情
    @GetMapping("/routeDetail")
    public ModelAndView findOne(Integer pid, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        Product product = productService.findOne(pid);

        Member member = (Member) session.getAttribute("member");

        if (member==null){
            modelAndView.addObject("favorite", false);
        }else {
            modelAndView.addObject("favorite", productService.memberFavoriteProduct(member.getMid(), product.getPid()));
        }
        modelAndView.addObject("product",product);
        modelAndView.setViewName("/frontdesk/route_detail");
        return modelAndView;
    }




}