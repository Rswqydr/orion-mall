package com.orion.goods.controller;

import com.github.pagehelper.PageInfo;
import com.orion.goods.service.CategoryBrandService;
import com.orion.goods.service.CategoryService;
import com.orion.pojo.CategoryBrand;
import entity.Result;
import entity.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationSupport;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/categoryBrand")
public class CategoryBrandController {
    @Autowired
    private CategoryBrandService categoryBrandService;
    private Logger log = LoggerFactory.getLogger(CategoryBrandController.class);

    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<CategoryBrand>> findPage(@RequestBody(required = true) CategoryBrand categoryBrand,
                                                    @PathVariable("page") int page,
                                                    @PathVariable("size") int size) {
        return new Result<>(true, StatusCode.OK, "查询成功", categoryBrandService.findPage(categoryBrand, page, size));
    }

    @GetMapping("/search//{page}/{size}")
    public Result<PageInfo<CategoryBrand>> findPage(@PathVariable("page") int page, @PathVariable("size") int size) {
        return new Result<>(true, StatusCode.OK, "查询成功", categoryBrandService.findPage(page, size));
    }

    @PostMapping("/search")
    public Result<List<CategoryBrand>> search(@RequestBody CategoryBrand categoryBrand) {
        return new Result<>(true, StatusCode.OK, "查询成功", categoryBrandService.findList(categoryBrand));
    }

    // 注意，通过请求方法区别增删改查，不用在url中指明
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") int id ) {
        categoryBrandService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功", null);
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody CategoryBrand categoryBrand, @PathVariable("id") int id) {
        categoryBrand.setBrandId(id);
        categoryBrandService.update(categoryBrand);
        return new Result(true, StatusCode.OK, "修改成功", null);
    }

    @PostMapping
    public Result add(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.add(categoryBrand);
        return new Result(true, StatusCode.OK, "添加成功", null);
    }

    @GetMapping("/{id}")
    public Result<CategoryBrand> find(@PathVariable int id) {
        return new Result(true, StatusCode.OK, "查询成功", categoryBrandService.findById(id));
    }

    @GetMapping
    public Result<List<CategoryBrand>> findAll() {
        return new Result(true, StatusCode.OK, "查询陈功", categoryBrandService.findAll());
    }


}



