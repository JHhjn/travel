package com.hniu.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hniu.travel.pojo.Product;

import java.util.List;

public interface ProductMapper extends BaseMapper<Product> {

    Page<Product> findProductPage(Page<Product> page);

    Product findOne(Integer pid);
    //查询用户是否收藏该产品
    Integer findMidHasPid(int mid,int pid);

    void add(int  mid, int pid);
    void del(int  mid, int pid);

    Page<Product> findMemberFavorite(Page<Product> page,Integer mid);

    //跟团游
    List<Product> findTour(Integer random);
}
