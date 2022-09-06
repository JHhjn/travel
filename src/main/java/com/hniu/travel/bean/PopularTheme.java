package com.hniu.travel.bean;

import com.hniu.travel.pojo.Product;
import com.hniu.travel.pojo.Theme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PopularTheme {
    //标签栏
    List<Theme> themeTitleList;

    //图片区
    List<Product> themeProductList;
}
