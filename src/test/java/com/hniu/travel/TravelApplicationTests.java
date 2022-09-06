package com.hniu.travel;

import com.hniu.travel.bean.*;
import com.hniu.travel.mapper.*;
import com.hniu.travel.pojo.Product;
import com.hniu.travel.service.*;
import com.hniu.travel.util.StringTools;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    @Autowired
    private HotMapper hotMapper;
    @Autowired
    private ThemeMapper themeMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private HotService hotService;
    @Autowired
    private ThemeService themeService;




    @Test
    public void t5(){
        String hjn = bCryptPasswordEncoder.encode("hjn");
        System.out.println(hjn);
    }

}
