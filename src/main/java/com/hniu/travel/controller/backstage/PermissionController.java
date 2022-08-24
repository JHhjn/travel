package com.hniu.travel.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hniu.travel.pojo.Permission;

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
@RequestMapping("/backstage/permission")
public class PermissionController {
    @Autowired
    private PermissionService service;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('/permission/all')")
    public ModelAndView all(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer size){

        Page<Permission> permissionPage = service.findPage(page, size);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("permissionPage",permissionPage);
        modelAndView.setViewName("/backstage/permission_all");
        return modelAndView;
    }

    @PostMapping("/add")
    public String add(Permission permission){
        service.addPermission(permission);
        return "redirect:/backstage/permission/all";
    }
    @GetMapping("/edit")
    public ModelAndView edit(Integer pid){
        Permission permission = service.findPermission(pid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("permission",permission);
        modelAndView.setViewName("/backstage/permission_edit");
        return modelAndView;
    }
    @PostMapping("/update")
    public String update(Permission permission){
        service.updatePermission(permission);
        return "redirect:/backstage/permission/all";
    }

    @GetMapping("/delete")
    public String delete(Integer pid){
        service.delPermission(pid);
        return "redirect:/backstage/permission/all";
    }
}
