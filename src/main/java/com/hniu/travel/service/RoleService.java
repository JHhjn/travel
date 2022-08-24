package com.hniu.travel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hniu.travel.bean.PermissionWithStatus;
import com.hniu.travel.mapper.PermissionMapper;
import com.hniu.travel.mapper.RoleMapper;
import com.hniu.travel.pojo.Admin;
import com.hniu.travel.pojo.Permission;
import com.hniu.travel.pojo.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    //分页查询角色
    public Page<Role> findPage(int page, int size){
        Page<Role> rolePage = roleMapper.selectPage(new Page(page, size), null);
        return rolePage;
    }
    //角色添加
    public void addRole(Role role){
        roleMapper.insert(role);
    }
    //通过rid查询角色
    public Role findRoleById(Integer rid){
        Role role = roleMapper.selectById(rid);
        return role;
    }

    //更新角色数据
    public  void updateRole( Role role){
        roleMapper.updateById(role);
    }

    //删除角色
    public void deleteRole(Integer rid){
        roleMapper.deleteById(rid);
    }

    //通过rid查询角色所拥有的权限
    public List<PermissionWithStatus> findPermissionByRid(Integer rid){
        //该角色拥有的所有权限id
        List<Integer> pidList = permissionMapper.findPermissionByRid(rid);
        //查询所有权限
        List<Permission> permissionList = permissionMapper.selectList(null);
        //创建带有状态的权限集合
        List<PermissionWithStatus> permissions=new ArrayList<>();

        for (Permission permission : permissionList) {
            //创建带有状态的权限
            PermissionWithStatus permissionWithStatus = new PermissionWithStatus();

            BeanUtils.copyProperties(permission,permissionWithStatus);
            //判断角色是否拥有该权限
            if (pidList.contains(permission.getPid())) {

                permissionWithStatus.setRoleHas(true);
            }else {
                permissionWithStatus.setRoleHas(false);
            }
            permissions.add(permissionWithStatus);
        }

        return permissions;
    }
    //更新角色所拥有的权限
    @Transactional(rollbackFor = Exception.class)
    public void updatePermissions(Integer rid,Integer[]  pids){
        //清空角色所拥有的权限
        permissionMapper.delByRid(rid);

        //为角色重新添加所勾选的权限
        for (Integer pid : pids) {
            permissionMapper.addPermissionByRid(rid,pid);
        }
    }
}
