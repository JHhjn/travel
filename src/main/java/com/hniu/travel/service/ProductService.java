package com.hniu.travel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hniu.travel.bean.PackageTour;
import com.hniu.travel.mapper.ProductMapper;
import com.hniu.travel.pojo.Product;
import com.hniu.travel.util.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Transactional
@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StringTools stringTools;

    public Page<Product> findPage(Integer page, Integer size){
        return productMapper.findProductPage(new Page(page,size));
    }

    public Product findProductById(Integer pid){
        return productMapper.selectById(pid);
    }

    public void addProduct(Product product){
        productMapper.insert(product);
    }

    public void updateProduct(Product product){
        productMapper.updateById(product);
    }

    public void delProduct(Integer pid){
        productMapper.deleteById(pid);
    }

    public void updateStatus(Integer pid){
        Product product = productMapper.selectById(pid);
        product.setStatus(!product.getStatus());
        productMapper.updateById(product);
    }

    public Product findOne(Integer pid){
     return  productMapper.findOne(pid);
    }

    public Page<Product> findProduct(Integer cid,String productName,Integer page,Integer size){
        QueryWrapper<Product> queryWrapper=new QueryWrapper<>();
        if (cid != null){
            queryWrapper.eq("cid", cid);
        }
        if (productName != null){
            queryWrapper.like("productName", productName);
        }
        //筛选出还在启用的产品
        queryWrapper.eq("status", 1);
        //倒序排列
        queryWrapper.orderByDesc("pid");

        Page selectPage = productMapper.selectPage(new Page(page, size), queryWrapper);

        return selectPage;
    }
    public Boolean memberFavoriteProduct(int mid,int pid){
        if (productMapper.findMidHasPid(mid,pid)!=null)return true;
        return false;
    }
    public void add(int mid,int pid){
        productMapper.add(mid, pid);
    }
    public void del(int mid,int pid){
        productMapper.del(mid,pid);
    }
    /**
     * 查询用户收藏的线路
     */
    public Page<Product> findMemberFavorite(int page, int size, Integer mid){
        Page<Product> favoriteProduct = productMapper.findMemberFavorite(new Page(page, size), mid);
        return favoriteProduct;
    }
    public PackageTour findTour(){
        List<Product> left = productMapper.findTour(0);
        for (Product product : left) {
            product.setProductName(stringTools.removeExcessText(product.getProductName()));
        }
        List<Product> right = productMapper.findTour(5);
        for (Product product : right) {
            product.setProductName(stringTools.removeExcessText(product.getProductName()));
        }
        return new PackageTour(left,right);
    }
}
