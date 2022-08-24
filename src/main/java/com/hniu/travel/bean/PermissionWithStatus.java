package com.hniu.travel.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class PermissionWithStatus {

    @TableId
    private Integer pid;
    private String permissionName; // 权限名
    private String permissionDesc;//权限详情
    private Boolean roleHas;//是否拥有该角色
}

