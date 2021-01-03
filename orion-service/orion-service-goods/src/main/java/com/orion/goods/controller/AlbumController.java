package com.orion.goods.controller;

import com.github.pagehelper.PageInfo;
import com.orion.goods.service.AlbumService;
import com.orion.pojo.Album;
import entity.Result;
import entity.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    private Logger log = LoggerFactory.getLogger(AlbumController.class);

    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Album>> findpage(@RequestBody Album album, @PathVariable("page")int page, @PathVariable("size")int size) {
        return new Result<>(true, StatusCode.OK, "查询成功", albumService.findPage(album, page, size));
    }

    @GetMapping("search/{page}/{size}")
    public Result<PageInfo<Album>> findpage(@PathVariable("page")int page, @PathVariable("size")int size) {
        return new Result<>(true, StatusCode.OK, "查询成功", albumService.findPage(page, size));
    }

    @PostMapping("/search")
    public Result<List<Album>> search(@RequestBody(required = false)Album album) {
        return new Result<>(true, StatusCode.OK, "查询成功", albumService.findList(album));
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id) {
        albumService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功", null);
    }

    @PutMapping("/update/{id}")
    public Result update(@RequestBody(required = false)Album album, @PathVariable("id")Long id) {
        album.setId(id);
        albumService.update(album);
        return new Result(true, StatusCode.OK, "修改成功", null);
    }

    @PostMapping("/add")
    public Result add(@RequestBody(required = true)Album album) {
        albumService.add(album);
        return new Result(true, StatusCode.OK, "添加成功", null);
    }

    @GetMapping("/search/{id}")
    public Result<Album> findone(@PathVariable("id")Long id ) {
        return new Result<>(true, StatusCode.OK, "查询成功", albumService.findById(id));
    }

    // 无条件查询
    @GetMapping
    public Result<List<Album>> findAll() {
        return new Result<>(true, StatusCode.OK, "查询成功", albumService.findAll());
    }
}
