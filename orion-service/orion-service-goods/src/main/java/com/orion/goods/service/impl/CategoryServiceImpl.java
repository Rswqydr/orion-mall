package com.orion.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orion.goods.dao.CategoryMapper;
import com.orion.goods.service.CategoryService;
import com.orion.pojo.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.swing.StringUIClientPropertyKey;
import tk.mybatis.mapper.entity.Example;


import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    private Logger log  = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public PageInfo<Category> findPage(Category category, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        // 构造条件
        Example example = createExample(category);
        // 查询
        return new PageInfo<>(categoryMapper.selectByExample(example));
    }

    @Override
    public PageInfo<Category> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(categoryMapper.selectAll());
    }

    @Override
    public List<Category> findList(Category category) {
        Example example = createExample(category);
        return categoryMapper.selectByExample(example);
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    @Override
    public void add(Category category) {
        categoryMapper.insertSelective(category);
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryMapper.selectAll();
    }

    @Override
    public List<Category> findByParentId(Integer pid) {
        Category category = new Category();
        category.setParentId(pid);
        return categoryMapper.select(category);
    }

    private Example createExample(Category category) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        if(category != null) {
            if(!StringUtils.isEmpty(category.getId())) {
                criteria.andEqualTo("id", category.getId());
            }
            if(!StringUtils.isEmpty(category.getName()) ){
                criteria.andLike("name", "%" + category.getName()+"%");
            }
            if(!StringUtils.isEmpty(category.getGoodsNum()) ){
                criteria.andEqualTo("goods_num", category.getGoodsNum());
            }
            if(!StringUtils.isEmpty(category.getIsShow()) ){
                criteria.andEqualTo("is_show", category.getIsShow());
            }
            if(!StringUtils.isEmpty(category.getSeq()) ){
                criteria.andEqualTo("seq", category.getSeq());
            }
            if(!StringUtils.isEmpty(category.getIsMenu()) ){
                criteria.andEqualTo("is_menu", category.getIsMenu());
            }
            if(!StringUtils.isEmpty(category.getParentId()) ){
                criteria.andEqualTo("parent_id", category.getParentId());
            }
            if(!StringUtils.isEmpty(category.getTemplateId()) ){
                criteria.andEqualTo("template_id", category.getTemplateId());
            }
        }
        return example;
    }
}
