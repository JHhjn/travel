package com.hniu.travel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hniu.travel.bean.RoleWithStatus;
import com.hniu.travel.mapper.AdminMapper;
import com.hniu.travel.mapper.RoleMapper;
import com.hniu.travel.pojo.Admin;
import com.hniu.travel.pojo.Permission;
import com.hniu.travel.pojo.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    //分页查询管理员
    public Page<Admin> findPage(int page,int size){
        Page<Admin> adminPage = adminMapper.selectPage(new Page(page, size), null);
        return adminPage;
    }

    //新增管理员
    public void addAdmin(Admin admin){
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        adminMapper.insert(admin);
    }

    //根据id查询管理员
    public Admin findAdminById(Integer id){
        Admin admin = adminMapper.selectById(id);
        return admin;
    }

    //更新管理员信息
    public void updateAdmin(Admin admin){
        String oldPassword = adminMapper.selectById(admin.getAid()).getPassword();
        String newPassword=admin.getPassword();
         if (!oldPassword.equals(newPassword)){
             admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
         }
        adminMapper.updateById(admin);
    }

    // 查询用户详情
    public Admin findDesc(Integer aid){
        return adminMapper.findDesc(aid);
    }

    // 查询用户的角色情况
    public List<RoleWithStatus> findRole(Integer aid){
        // 查询所有角色
        List<Role> roles = roleMapper.selectList(null);
        // 查询用户拥有的所有角色id
        List<Integer> rids = roleMapper.findRoleById(aid);

        // 带有状态的角色集合
        List<RoleWithStatus> roleList = new ArrayList();
        // 遍历所有角色
        for (Role role:roles){
            // 创建带有状态的角色
            RoleWithStatus roleWithStatus = new RoleWithStatus();
            // 将角色属性复制给带有状态的角色
            BeanUtils.copyProperties(role,roleWithStatus);
            if(rids.contains(role.getRid())){ //用户拥有该角色，角色状态设置为true
                roleWithStatus.setAdminHas(true);
            }else { //用户不拥有该角色，角色状态设置为false
                roleWithStatus.setAdminHas(false);
            }
            roleList.add(roleWithStatus);
        }
        return roleList;
    }

    //更新用户拥有的角色
    @Transactional(rollbackFor = Exception.class)
    public void updateRoles(Integer aid,Integer[] ids){
        //根据aid删除所拥有的角色
        roleMapper.delByAid(aid);
        //为用户添加角色
        for (Integer id : ids) {
            roleMapper.addRoleByAid(aid,id);
        }
    }
    //修改用户状态
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer aid){
        //查询要修改的状态
        Admin admin = adminMapper.selectById(aid);
        //设置状态
        admin.setStatus(!admin.isStatus());
        //更新用户状态
        updateAdmin(admin);
    }
    // 根据名字查询管理员
    public Admin findByAdminName(String username) {
        QueryWrapper<Admin> wrapper = new QueryWrapper();
        wrapper.eq("username", username);
        Admin admin = adminMapper.selectOne(wrapper);
        return admin;
    }

    //根据名字查询管理员
    public Admin findAdminByUsername(String username){
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("username",username);
        Admin admin = adminMapper.selectOne(adminQueryWrapper);
        return admin;
    }

    // 根据名字查询权限
    public List<Permission> findAllPermission(String username) {
        return adminMapper.findAllPermission(username);
    }

}
