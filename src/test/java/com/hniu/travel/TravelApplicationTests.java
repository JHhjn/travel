package com.hniu.travel;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hniu.travel.bean.*;
import com.hniu.travel.mapper.AdminMapper;
import com.hniu.travel.mapper.PermissionMapper;
import com.hniu.travel.mapper.ProductMapper;
import com.hniu.travel.mapper.RoleMapper;
import com.hniu.travel.pojo.Admin;
import com.hniu.travel.pojo.Permission;
import com.hniu.travel.pojo.Product;
import com.hniu.travel.pojo.Role;
import com.hniu.travel.service.*;
import com.hniu.travel.util.StringTools;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TravelApplicationTests {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StringTools stringTools;
    @Autowired
    private WeatherService weatherService;




    @Test
    public void t6(){
        PackageTour tour = productService.findTour();
        System.out.println(tour);
    }
    @Test
    public void t7(){
        Weather weatherInfo = weatherService.getWeatherInfo("长沙市");
        List<WheatherDetails> lives = weatherInfo.getLives();
        System.out.println(lives.get(0));
    }
}
