package com.orion.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orion.goods.dao.AlbumMapper;
import com.orion.goods.service.AlbumService;
import com.orion.pojo.Album;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumMapper albumMapper;
    private Logger log = LoggerFactory.getLogger(AlbumServiceImpl.class);

    @Override
    public PageInfo<Album> findPage(Album album, int page, int size) {
        // 分页
        PageHelper.startPage(page, size);
        //  搜索条件构建
        Example example = createException(album);
        // 分页
        return new PageInfo<Album>(albumMapper.selectByExample(example));
    }

    @Override
    public PageInfo<Album> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        return new PageInfo<Album>(albumMapper.selectAll());
    }

    // 这种最好构建条件搜索
    @Override
    public List<Album> findList(Album album) {
        Example example = createException(album);
        return albumMapper.selectByExample(example);
    }

    @Override
    public void delete(Long id) {
        albumMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Album album) {
        albumMapper.updateByPrimaryKeySelective(album);
    }

    @Override
    public void add(Album album) {
        // 先判断是否存在吧， 如果存在就不能添加
        albumMapper.insert(album);
    }

    @Override
    public Album findById(Long id) {
        return albumMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Album> findAll() {
        return albumMapper.selectAll();
    }

    private Example createException(Album album) {
        Example example = new Example(Album.class);
        Example.Criteria criteria = example.createCriteria();
        if(album != null) {
            if(!StringUtils.isEmpty(album.getId())){
                criteria.andEqualTo("id", album.getId());
            }
            if(!StringUtils.isEmpty(album.getTitle())){
                criteria.andLike("title", "%"+ album.getTitle() + "%");
            }
            if(!StringUtils.isEmpty(album.getImage())){
                criteria.andEqualTo("image", album.getImage());
            }
            if(!StringUtils.isEmpty(album.getImageItems())){
                criteria.andEqualTo("image_items", album.getImageItems());
            }
        }
        return example;
    }
}
