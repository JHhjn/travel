package com.hniu.travel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hniu.travel.mapper.PermissionMapper;
import com.hniu.travel.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    //分页查询
    public Page<Permission> findPage(Integer page,Integer size){
        Page<Permission> permissionPage = permissionMapper.selectPage(new Page<Permission>(page, size), null);
        return permissionPage;
    }

    //根据pid查询权限信息
    public Permission  findPermission(Integer pid){
        Permission permission = permissionMapper.selectById(pid);
        return permission;
    }
    //更新指定的权限信息
    public void updatePermission(Permission permission){
        permissionMapper.updateById(permission);
    }

    //新增权限
    public void addPermission(Permission permission){
        permissionMapper.insert(permission);
    }
    //删除权限
    public void delPermission(Integer pid){
        permissionMapper.deleteById(pid);
    }

}
