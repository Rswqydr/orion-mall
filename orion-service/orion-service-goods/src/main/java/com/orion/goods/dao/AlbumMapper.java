package com.orion.goods.dao;
import com.orion.pojo.Album;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:Album的Dao
 * @Date 2019/6/14 0:12
 *****/
@Component
public interface AlbumMapper extends Mapper<Album> {
}
