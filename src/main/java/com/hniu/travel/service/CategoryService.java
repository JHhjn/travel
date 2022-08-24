package com.hniu.travel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hniu.travel.mapper.CategoryMapper;
import com.hniu.travel.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public Page<Category> findPage(Integer page, Integer size){
        return categoryMapper.selectPage(new Page(page,size), null);
    }

    public Category findCategoryById(Integer cid){
        return categoryMapper.selectById(cid);
    }

    public void addCategory(Category category){
        categoryMapper.insert(category);
    }

    public void updateCategory(Category category){
        categoryMapper.updateById(category);
    }

    public void delCategory(Integer cid){
        categoryMapper.deleteById(cid);
    }

    public List<Category> findAll() {
        return categoryMapper.selectList(null);
    }
}
