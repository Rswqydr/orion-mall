package com.orion.goods.dao;
import com.orion.order.pojo.OrderItem;
import com.orion.pojo.Sku;
//import com.orion.pojo.OrderItem;
import org.apache.ibatis.annotations.Update;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:Sku的Dao
 * @Date 2019/6/14 0:12
 *****/
@Component
public interface SkuMapper extends Mapper<Sku> {

    // @Update(value="update tb_sku set num=num-#{num},sale_num=sale_num+#{num} where id =#{skuId} and num >=#{num}")
    // public int decrCount(OrderItem orderItem);
    @Update(value = "update tb_sku set num=num-#{num}, sale_num=sale_num+#{num} where id = #{skuId} and num >=#{num}")
    public int dectCount(OrderItem orderItem);
}
