package com.orion.goods.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orion.goods.dao.BrandMapper;
import com.orion.goods.service.BrandService;
import com.orion.pojo.Brand;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    private Logger log = LoggerFactory.getLogger(BrandService.class);

    @Override
    public PageInfo<Brand> findPage(Brand brand, int page, int size) {
        log.info("brand条件分页查询");
        // 分页
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(brand);
        // 执行搜索
        return new PageInfo<Brand>(brandMapper.selectByExample(example));
    }

    @Override
    public PageInfo<Brand> findPage(int page, int size) {
        log.info("brand分页查询");
        // 构建分页
        PageHelper.startPage(page, size);
        return new PageInfo<Brand>(brandMapper.selectAll());
    }

    @Override
    public List<Brand> findList(Brand brand) {
        log.info("条件查询商品列表");
        //构建条件查询
        Example example = createExample(brand);
        return brandMapper.selectByExample(example);
    }

    @Override
    public void delete(Integer id) {
        log.info("根据id删除商品");
        brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Brand brand) {
        log.info("更新商品");
        brandMapper.updateByPrimaryKey(brand);
    }

    @Override
    public void add(Brand brand) {
        log.info("添加商品");
        brandMapper.insert(brand);
    }

    @Override
    public Brand findById(Integer id) {
        log.info("通过主键查找商品");
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Brand> findAll() {
        log.info("查询所有商品：无条件");
        return brandMapper.selectAll();
    }

    @Override
    public List<Brand> findByCategory(Integer id) {
        log.info("查看商品分类层级");
        return brandMapper.findByCategory(id);
    }

    /**
     * 构建条件查询example
     * @param brand
     * @return
     */
    public Example createExample(Brand brand){
        Example example=new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if(brand!=null){
            // 品牌id
            if(brand.getId() != null){
                criteria.andEqualTo("id",brand.getId());
            }
            // 品牌名称
            if(!StringUtils.isEmpty(brand.getName())){
                criteria.andLike("name","%"+brand.getName()+"%");
            }
            // 品牌图片地址
            if(!StringUtils.isEmpty(brand.getImage())){
                criteria.andEqualTo("image",brand.getImage());
            }
            // 品牌的首字母
            if(!StringUtils.isEmpty(brand.getLetter())){
                criteria.andEqualTo("letter",brand.getLetter());
            }
            // 排序
            if(brand.getSeq() != null){
                criteria.andEqualTo("seq",brand.getSeq());
            }
        }
        return example;
    }
}
