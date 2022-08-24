package com.hniu.travel.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hniu.travel.bean.PermissionWithStatus;
import com.hniu.travel.pojo.Role;
import com.hniu.travel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/backstage/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('/role/all')")
    public ModelAndView all(@RequestParam(defaultValue = "1") int page
            ,@RequestParam(defaultValue = "10") int size){
        Page<Role> rolePage = roleService.findPage(page, size);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("rolePage",rolePage);
        modelAndView.setViewName("/backstage/role_all");
        return modelAndView;
    }

    @PostMapping("/add")
    public String add(Role role){
        roleService.addRole(role);
        return "redirect:/backstage/role/all";
    }
    @GetMapping("/edit")
    public ModelAndView edit(Integer rid){
        Role role = roleService.findRoleById(rid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("role",role);
        modelAndView.setViewName("/backstage/role_edit");
        return modelAndView;
    }
    @PostMapping("/update")
    public String update(Role role){
        roleService.updateRole(role);
        return "redirect:/backstage/role/all";
    }

    @GetMapping("/delete")
    public String delete(Integer rid){
        roleService.deleteRole(rid);
        return "redirect:/backstage/role/all";
    }
    @GetMapping("/findPermission")
    public ModelAndView findPermission(Integer rid){
        List<PermissionWithStatus> permissions = roleService.findPermissionByRid(rid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("permissions",permissions);
        modelAndView.addObject("rid",rid);
        modelAndView.setViewName("/backstage/role_permission");
        return modelAndView;
    }
    @PostMapping("/updatePermission")
    public String updatePermission(Integer rid ,Integer[] pids){
        roleService.updatePermissions(rid,pids);
        return "redirect:/backstage/role/all";
    }
}
