package com.hniu.travel.bean;

import com.hniu.travel.pojo.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PackageTour {
    private List<Product> left;
    private List<Product> right;
}
