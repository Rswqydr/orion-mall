package com.orion.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orion.goods.dao.CategoryBrandMapper;
import com.orion.pojo.Category;
import com.orion.pojo.CategoryBrand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryBrandServiceImpl implements com.orion.goods.service.CategoryBrandService {

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;
    private Logger log = LoggerFactory.getLogger(CategoryBrandServiceImpl.class);

    @Override
    public PageInfo<CategoryBrand> findPage(CategoryBrand categoryBrand, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 条件搜索
        Example example = createExample(categoryBrand);
        return new PageInfo<>(categoryBrandMapper.selectByExample(example));
    }

    @Override
    public PageInfo<CategoryBrand> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(categoryBrandMapper.selectAll());
    }

    @Override
    public List<CategoryBrand> findList(CategoryBrand categoryBrand) {
        Example example = createExample(categoryBrand);
        return categoryBrandMapper.selectByExample(example);
    }

    @Override
    public void delete(Integer id) {
        categoryBrandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(CategoryBrand categoryBrand) {
        categoryBrandMapper.updateByPrimaryKey(categoryBrand);
    }

    @Override
    public void add(CategoryBrand categoryBrand) {
        categoryBrandMapper.insertSelective(categoryBrand);
    }

    @Override
    public CategoryBrand findById(Integer id) {
        return categoryBrandMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CategoryBrand> findAll() {
        return categoryBrandMapper.selectAll();
    }

    private Example createExample(CategoryBrand categoryBrand) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        if(categoryBrand!=null) {
            if(StringUtils.isEmpty(categoryBrand.getCategoryId())){
                criteria.andEqualTo("category_id", categoryBrand.getCategoryId());
            }
            if(StringUtils.isEmpty(categoryBrand.getBrandId())){
                criteria.andEqualTo("brand_id",categoryBrand.getBrandId());
            }
        }
        return example;
    }
}
