package com.hniu.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hniu.travel.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

    //通过Rid查询角色拥有的权限id
    public List<Integer> findPermissionByRid(Integer rid);

    //根据rid删除所有权限
    void delByRid(Integer rid);

    //根据rid添加权限
    void addPermissionByRid(@Param("rid") Integer  rid,@Param("pid") Integer pid);
}
