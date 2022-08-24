package com.hniu.travel.controller.frontdesk;

import com.hniu.travel.bean.Result;
import com.hniu.travel.pojo.Member;
import com.hniu.travel.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/frontdesk/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ModelAndView register(Member member, String checkCode, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();

        String sessionCheckCode = (String) session.getAttribute("checkCode");
        if (!sessionCheckCode.equalsIgnoreCase(checkCode)){
            modelAndView.addObject("message","验证码错误");
            modelAndView.setViewName("/frontdesk/register");
            return modelAndView;
        }
       //注册
        Result result = memberService.register(member);
        if (!result.isFlag()){//注册失败
            modelAndView.addObject("message",result.getMessage());
            modelAndView.setViewName("/frontdesk/register");
        }else {
            modelAndView.setViewName("/frontdesk/register_ok");
        }
        return modelAndView;
    }

    @RequestMapping("/active")
    public ModelAndView active(String activeCode) {
        ModelAndView modelAndView = new ModelAndView();
        String active = memberService.active(activeCode);
        modelAndView.addObject("message",active);
        modelAndView.setViewName("/frontdesk/active_result");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView register(String name,String password,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        Result result = memberService.login(name,password);
        if(!result.isFlag()){ // 登录失败
            modelAndView.addObject("message",result.getMessage());
            modelAndView.setViewName("/frontdesk/login");
        }else{ // 登录成功\
            Member data = (Member) result.getData();
            session.setAttribute("member",data); // 将用户信息存入session
            modelAndView.setViewName("redirect:/frontdesk/index");
        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("member");
        return "redirect:/frontdesk/index";
    }
}
