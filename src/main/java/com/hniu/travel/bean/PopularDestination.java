package com.hniu.travel.bean;

import com.hniu.travel.pojo.Product;
import com.hniu.travel.pojo.Hot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PopularDestination {
    //标签栏
    private List<Hot> hotTitleList;
    //图片区
    private List<Product> hotProductList;
}
