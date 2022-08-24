package com.hniu.travel.security;

import com.hniu.travel.pojo.Admin;
import com.hniu.travel.pojo.Permission;
import com.hniu.travel.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            //认证
        Admin admin = adminService.findAdminByUsername(username);
        if (admin==null){
            throw new UsernameNotFoundException("用户不存在，请输入正确的用户名");
        }
        if (!admin.isStatus()){
            throw new UsernameNotFoundException("用户不可用，请与管理员联系");
        }

        //授权
        List<Permission> permissions = adminService.findAllPermission(username);
        //转换成userDetail认识的权限
        List<GrantedAuthority> list =new ArrayList<>();
        for (Permission permission : permissions) {

            list.add(new SimpleGrantedAuthority(permission.getPermissionDesc()));
        }
            

        //封装成UserDetail对象
        UserDetails userDetails = User.withUsername(admin.getUsername())
                .password(admin.getPassword())
                .authorities(list)
                .build();

        return userDetails;
    }
}
