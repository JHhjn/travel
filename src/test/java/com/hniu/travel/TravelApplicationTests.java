package com.hniu.travel;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hniu.travel.bean.PermissionWithStatus;
import com.hniu.travel.bean.RoleWithStatus;
import com.hniu.travel.mapper.AdminMapper;
import com.hniu.travel.mapper.PermissionMapper;
import com.hniu.travel.mapper.ProductMapper;
import com.hniu.travel.mapper.RoleMapper;
import com.hniu.travel.pojo.Admin;
import com.hniu.travel.pojo.Permission;
import com.hniu.travel.pojo.Product;
import com.hniu.travel.pojo.Role;
import com.hniu.travel.service.AdminService;
import com.hniu.travel.service.PermissionService;
import com.hniu.travel.service.ProductService;
import com.hniu.travel.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    void contextLoads() {
        Page<Admin> page = adminService.findPage(1, 5);
        System.out.println(page);
    }
    @Test
    public void t1(){
        List<Integer> permissions = permissionMapper.findPermissionByRid(1);
        System.out.println(permissions);
    }

    @Test
    public void t2(){
        List<PermissionWithStatus> permissions = roleService.findPermissionByRid(1);
        System.out.println(permissions);
    }
    @Test
    public void t3(){
        Page<Permission> page = permissionService.findPage(1, 10);
        System.out.println(page);
    }
    @Test
    public void t4(){
      for (int i=1;i<=512;i++)
      {
          Product one = productService.findOne(i);
          one.setProductDesc("<p>"+one.getProductDesc()+"</p>");
          productService.updateProduct(one);
      }
    }
    @Test
    public void t5(){
        List<Product> products = productMapper.selectList(new QueryWrapper<Product>().like("productName", "广西"));
        for (Product product : products) {
            product.setCid(10);
            productMapper.updateById(product);
        }
    }
}
