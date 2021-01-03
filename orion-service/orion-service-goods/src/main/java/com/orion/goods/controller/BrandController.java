package com.orion.goods.controller;

import com.github.pagehelper.PageInfo;
import com.orion.goods.service.BrandService;
import com.orion.pojo.Brand;
import entity.Result;
import entity.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    private Logger log = LoggerFactory.getLogger(BrandController.class);

    // 分页条件搜索查询
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody Brand brand, @PathVariable int page, @PathVariable int size) {
        log.info("条件分页控制器");
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功",brandService.findPage(brand, page, size));
    }
    // 分页展示
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        log.info("分页控制器");
        return new Result<PageInfo>(true, StatusCode.OK,"查询成功", brandService.findPage(page, size));
    }
    // 多条件搜索
    @PostMapping(value = "/search" )
    public Result<List<Brand>> search(@RequestBody(required = false)Brand brand) {
        return new Result<>(true, StatusCode.OK, "查询成功", brandService.findList(brand));
    }

    // 根据id删除品牌数据
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        //调用BrandService实现根据主键删除
        brandService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    // 使用put修改数据
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  Brand brand,@PathVariable Integer id){
        //设置主键值
        brand.setId(id);
        //调用BrandService实现修改Brand
        brandService.update(brand);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    // 添加品牌数据
    @PostMapping
    public Result add(@RequestBody   Brand brand){
        //调用BrandService实现添加Brand
        brandService.add(brand);
        return new Result(true,StatusCode.OK,"添加成功");
    }


    @GetMapping("/{id}")
    public Result<Brand> findById(@PathVariable Integer id){
        //调用BrandService实现根据主键查询Brand
        Brand brand = brandService.findById(id);
        return new Result<Brand>(true,StatusCode.OK,"查询成功",brand);
    }


    @GetMapping
    public Result<List<Brand>> findAll(){
        try {
            log.info("高并发测试查询所有数据：start-sleep");
            Thread.sleep(3000);
            log.info("高并发测试查询所有数据：end-sleep");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //调用BrandService实现查询所有Brand
        List<Brand> list = brandService.findAll();
        return new Result<List<Brand>>(true, StatusCode.OK,"查询成功",list) ;
    }


    @GetMapping("/category/{id}")
    public Result<List<Brand>> findBrandByCategory(@PathVariable(name="id") Integer id){
        List<Brand> brandList = brandService.findByCategory(id);
        return new Result<List<Brand>>(true,StatusCode.OK,"查询品牌列表成功",brandList);
    }
}
