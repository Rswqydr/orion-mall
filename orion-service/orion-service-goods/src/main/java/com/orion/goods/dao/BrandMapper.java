package com.orion.goods.dao;
import com.orion.pojo.Brand;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/****
 * @Author:admin
 * @Description:Brand的Dao
 * @Date 2019/6/14 0:12
 *****/
@Component
public interface BrandMapper extends Mapper<Brand> {

    @Select(value = "select tb.* from tb_brand tb, tb_category_brand tab where tb.id = tab.brand_id and tbc.category_id=#{categoryId}")
    List<Brand> findByCategory(Integer categoryId);
}
