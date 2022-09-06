package com.hniu.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hniu.travel.pojo.Admin;
import com.hniu.travel.pojo.Permission;
import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {
    //查询用户详情
    Admin findDesc(Integer aid);

    // 根据管理员名查询权限
    List<Permission> findAllPermission(String username);

}
