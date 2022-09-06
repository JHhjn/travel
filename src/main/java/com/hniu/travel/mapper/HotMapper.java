package com.hniu.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hniu.travel.pojo.Product;
import com.hniu.travel.pojo.Hot;

public interface HotMapper extends BaseMapper<Hot> {

    Product findHotByHid(Integer hid);
}
