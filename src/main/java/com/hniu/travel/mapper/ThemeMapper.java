package com.hniu.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hniu.travel.pojo.Product;
import com.hniu.travel.pojo.Theme;

public interface ThemeMapper extends BaseMapper<Theme> {

    Product findThemeByThid(Integer thid);
}
