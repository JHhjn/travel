package com.hniu.travel.mapper;

import com.hniu.travel.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86131
* @description 针对表【role】的数据库操作Mapper
* @createDate 2022-08-16 09:21:01
* @Entity com.hniu.travel.pojo.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    // 查询用户拥有的所有角色的id
    List<Integer> findRoleById(Integer aid);

    //根据aid删除所有角色
    void delByAid(Integer aid);

    //根据aid添加角色
    void addRoleByAid(@Param("aid") Integer  aid,@Param("rid") Integer rid);

}




