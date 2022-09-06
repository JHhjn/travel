package com.hniu.travel.bean;

import com.hniu.travel.pojo.Hot;
import com.hniu.travel.pojo.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelAround {

    //标签栏
    private List<Hot> hotTitleList;

    //周边图片区
    private List<Product> travelAroundList;
}
