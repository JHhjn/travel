package com.hniu.travel.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hniu.travel.pojo.Permission;
import lombok.Data;

import java.util.List;
@Data
public class RoleWithStatus {

    @TableId
    private Integer rid;
    private String roleName; // 角色名
    private String roleDesc; // 角色介绍
    private Boolean AdminHas;//是否拥有该角色
}

