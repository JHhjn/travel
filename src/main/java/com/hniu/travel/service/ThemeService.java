package com.hniu.travel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hniu.travel.bean.PopularTheme;
import com.hniu.travel.mapper.ThemeMapper;
import com.hniu.travel.pojo.Product;
import com.hniu.travel.pojo.Theme;
import com.hniu.travel.util.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThemeService {
    @Autowired
    private ThemeMapper themeMapper;
    @Autowired
    private StringTools stringTools;

    public PopularTheme findPopularTheme(){
        //1.导航栏数据
        QueryWrapper<Theme> themeQueryWrapper = new QueryWrapper<>();
        themeQueryWrapper.eq("display", 1);
        List<Theme> themeTitles = themeMapper.selectList(themeQueryWrapper);

        //2.图片区数据
        List<Product> themeProductList=new ArrayList<>();
        for (Theme themeTitle : themeTitles) {
            Product theme = themeMapper.findThemeByThid(themeTitle.getThid());
            theme.setProductName(stringTools.removeExcessText(theme.getProductName()));
            themeProductList.add(theme);
        }


        return new PopularTheme(themeTitles,themeProductList);
    }
}
