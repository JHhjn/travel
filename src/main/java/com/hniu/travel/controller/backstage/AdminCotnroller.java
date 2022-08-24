package com.hniu.travel.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hniu.travel.bean.RoleWithStatus;
import com.hniu.travel.pojo.Admin;
import com.hniu.travel.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/backstage/admin")
public class AdminCotnroller {
    @Autowired
    private AdminService adminService;


    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('/admin/all')")
    public ModelAndView all(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size){
        ModelAndView modelAndView = new ModelAndView();
        Page<Admin> adminPage = adminService.findPage(page, size);
        modelAndView.addObject("adminPage",adminPage);
        modelAndView.setViewName("/backstage/admin_all");
        return modelAndView;
    }
    @PostMapping("/add")
    public String add(Admin admin){
        adminService.addAdmin(admin);
        return "redirect:/backstage/admin/all";
    }
    @GetMapping("/edit")
    public ModelAndView edit(int aid){
        ModelAndView modelAndView = new ModelAndView();
        Admin admin = adminService.findAdminById(aid);
        modelAndView.addObject("admin",admin);
        modelAndView.setViewName("/backstage/admin_edit");
        return modelAndView;
    }

    @PostMapping("/update")
    public String update(Admin admin){
        adminService.updateAdmin(admin);
        return "redirect:/backstage/admin/all";
    }

    @GetMapping("/desc")
    public ModelAndView desc(Integer aid){
        Admin admin = adminService.findDesc(aid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("admin", admin);
        modelAndView.setViewName("/backstage/admin_desc");
        return modelAndView;
    }
    @GetMapping("/findRole")
    public ModelAndView findRole(Integer aid){
        List<RoleWithStatus> roles = adminService.findRole(aid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("roles", roles);
        modelAndView.addObject("aid",aid);
        modelAndView.setViewName("/backstage/admin_role");
        return modelAndView;
    }

    @PostMapping("/updateRole")
    public String updateRole( Integer aid,Integer[] ids){
        adminService.updateRoles(aid,ids);
        return "redirect:/backstage/admin/all";
    }

    @GetMapping("/updateStatus")
    public String updateStatus(Integer aid){
        adminService.updateStatus(aid);
        return "redirect:/backstage/admin/all";    }
}
