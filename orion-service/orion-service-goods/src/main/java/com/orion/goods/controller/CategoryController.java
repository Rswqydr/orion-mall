package com.orion.goods.controller;

import com.github.pagehelper.PageInfo;
import com.orion.goods.service.CategoryService;
import com.orion.pojo.Category;
import entity.Result;
import entity.StatusCode;
import org.bouncycastle.cms.PasswordRecipientId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    private Logger log = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Category>> findPage(@RequestBody Category category, @PathVariable int page, @PathVariable int size) {
        return new Result<PageInfo<Category>>(true, StatusCode.OK, "查询成功", categoryService.findPage(category, page, size));
    }

    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        return new Result<>(true, StatusCode.OK, "查询成功", categoryService.findPage(page, size));
    }

    /***
     * 多条件搜索品牌数据
     * @param category
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<Category>> findList(@RequestBody(required = false)  Category category){
        //调用CategoryService实现条件查询Category
        List<Category> list = categoryService.findList(category);
        return new Result<List<Category>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        //调用CategoryService实现根据主键删除
        categoryService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改Category数据
     * @param category
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  Category category,@PathVariable Integer id){
        //设置主键值
        category.setId(id);
        //调用CategoryService实现修改Category
        categoryService.update(category);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增Category数据
     * @param category
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   Category category){
        //调用CategoryService实现添加Category
        categoryService.add(category);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询Category数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Category> findById(@PathVariable Integer id){
        //调用CategoryService实现根据主键查询Category
        Category category = categoryService.findById(id);
        return new Result<Category>(true,StatusCode.OK,"查询成功",category);
    }

    /***
     * 查询Category全部数据
     * @return
     */
    @GetMapping
    public Result<List<Category>> findAll(){
        //调用CategoryService实现查询所有Category
        List<Category> list = categoryService.findAll();
        return new Result<List<Category>>(true, StatusCode.OK,"查询成功",list) ;
    }

    /**
     *  根据父ID 查询该分类下的所有的子分类列表   如果是一级分类 pid = 0
     * @param pid
     * @return
     */
    @GetMapping("/list/{pid}")
    public Result<List<Category>> findByParentId(@PathVariable(name="pid") Integer pid){
        //SELECT * from tb_category where parent_id=0
        List<Category> categoryList = categoryService.findByParentId(pid);
        return new Result<List<Category>>(true,StatusCode.OK,"查询分类列表成功",categoryList);
    }
}
