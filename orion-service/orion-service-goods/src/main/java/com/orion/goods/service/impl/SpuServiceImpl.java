package com.orion.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.CacheAdapter;
import com.netflix.discovery.converters.Auto;
import com.orion.goods.dao.BrandMapper;
import com.orion.goods.dao.CategoryMapper;
import com.orion.goods.dao.SkuMapper;
import com.orion.goods.dao.SpuMapper;
import com.orion.goods.service.SpuService;
import com.orion.pojo.*;
import entity.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SpuServiceImpl implements SpuService {
    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private BrandMapper brandMapper;

    /**
     * Spu条件+分页查询
     *
     * @param spu  查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Spu> findPage(Spu spu, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //搜索条件构建  排除掉 已删除的
        Example example = createExample(spu);
        //执行搜索
        return new PageInfo<Spu>(spuMapper.selectByExample(example));
    }

    /**
     * Spu分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Spu> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<Spu>(spuMapper.selectAll());
    }

    /**
     * Spu条件查询
     *
     * @param spu
     * @return
     */
    @Override
    public List<Spu> findList(Spu spu) {
        //构建查询条件
        Example example = createExample(spu);
        //根据构建的条件查询数据
        return spuMapper.selectByExample(example);
    }


    /**
     * Spu构建查询对象
     *
     * @param spu
     * @return
     */
    public Example createExample(Spu spu) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete",0);//只找 没有被删除的
        if (spu != null) {
            // 主键
            if (!StringUtils.isEmpty(spu.getId())) {
                criteria.andEqualTo("id", spu.getId());
            }
            // 货号
            if (!StringUtils.isEmpty(spu.getSn())) {
                criteria.andEqualTo("sn", spu.getSn());
            }
            // SPU名
            if (!StringUtils.isEmpty(spu.getName())) {
                criteria.andLike("name", "%" + spu.getName() + "%");
            }
            // 副标题
            if (!StringUtils.isEmpty(spu.getCaption())) {
                criteria.andEqualTo("caption", spu.getCaption());
            }
            // 品牌ID
            if (!StringUtils.isEmpty(spu.getBrandId())) {
                criteria.andEqualTo("brandId", spu.getBrandId());
            }
            // 一级分类
            if (!StringUtils.isEmpty(spu.getCategory1Id())) {
                criteria.andEqualTo("category1Id", spu.getCategory1Id());
            }
            // 二级分类
            if (!StringUtils.isEmpty(spu.getCategory2Id())) {
                criteria.andEqualTo("category2Id", spu.getCategory2Id());
            }
            // 三级分类
            if (!StringUtils.isEmpty(spu.getCategory3Id())) {
                criteria.andEqualTo("category3Id", spu.getCategory3Id());
            }
            // 模板ID
            if (!StringUtils.isEmpty(spu.getTemplateId())) {
                criteria.andEqualTo("templateId", spu.getTemplateId());
            }
            // 运费模板id
            if (!StringUtils.isEmpty(spu.getFreightId())) {
                criteria.andEqualTo("freightId", spu.getFreightId());
            }
            // 图片
            if (!StringUtils.isEmpty(spu.getImage())) {
                criteria.andEqualTo("image", spu.getImage());
            }
            // 图片列表
            if (!StringUtils.isEmpty(spu.getImages())) {
                criteria.andEqualTo("images", spu.getImages());
            }
            // 售后服务
            if (!StringUtils.isEmpty(spu.getSaleService())) {
                criteria.andEqualTo("saleService", spu.getSaleService());
            }
            // 介绍
            if (!StringUtils.isEmpty(spu.getIntroduction())) {
                criteria.andEqualTo("introduction", spu.getIntroduction());
            }
            // 规格列表
            if (!StringUtils.isEmpty(spu.getSpecItems())) {
                criteria.andEqualTo("specItems", spu.getSpecItems());
            }
            // 参数列表
            if (!StringUtils.isEmpty(spu.getParaItems())) {
                criteria.andEqualTo("paraItems", spu.getParaItems());
            }
            // 销量
            if (!StringUtils.isEmpty(spu.getSaleNum())) {
                criteria.andEqualTo("saleNum", spu.getSaleNum());
            }
            // 评论数
            if (!StringUtils.isEmpty(spu.getCommentNum())) {
                criteria.andEqualTo("commentNum", spu.getCommentNum());
            }
            // 是否上架
            if (!StringUtils.isEmpty(spu.getIsMarketable())) {
                criteria.andEqualTo("isMarketable", spu.getIsMarketable());
            }
            // 是否启用规格
            if (!StringUtils.isEmpty(spu.getIsEnableSpec())) {
                criteria.andEqualTo("isEnableSpec", spu.getIsEnableSpec());
            }
            // 是否删除
            if (!StringUtils.isEmpty(spu.getIsDelete())) {
                criteria.andEqualTo("isDelete", spu.getIsDelete());
            }
            // 审核状态
            if (!StringUtils.isEmpty(spu.getStatus())) {
                criteria.andEqualTo("status", spu.getStatus());
            }
        }
        return example;
    }

    @Override
    public void delete(Long id) {
        // 实际从磁盘上删除，要求检测为逻辑删除后才可执行
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if(spu == null){
            throw new RuntimeException("商品不存在");
        }if(spu.getIsDelete().equals("1")){
            throw new RuntimeException("必须先逻辑删除");
        }
        spuMapper.deleteByPrimaryKey(id);

    }

    /**
     * 修改Spu
     *
     * @param spu
     */
    @Override
    public void update(Spu spu) {
        spuMapper.updateByPrimaryKey(spu);
    }

    /**
     * 增加Spu
     *
     * @param spu
     */
    @Override
    public void add(Spu spu) {
        spuMapper.insert(spu);
    }

    /**
     * 根据ID查询Spu
     *
     * @param id
     * @return
     */
    @Override
    public Spu findById(Long id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Spu全部数据
     *
     * @return
     */
    @Override
    public List<Spu> findAll() {
        return spuMapper.selectAll();
    }

    @Override
    public void save(Goods goods) {
        // 处理逻辑
        /*
        如果当前spuid不存在，则使用idworker创建一个spuid，并且将该spuselective插入
        如果当前id存在，首先修改当前spu， 然后找出所有spuid为该spuid的sku，然后删除所有的sku
        之后在将当前spu下绑定的sku数据加入进去
         */
        Spu spu = goods.getSpu();
        if(StringUtils.isEmpty(spu.getId())) {
            spu.setId(idWorker.nextId()); // 使用雪花算法创建一个id
            spuMapper.insertSelective(spu);
        }else {
            spuMapper.updateByPrimaryKey(spu);
            Sku condition = new Sku();
            condition.setSpuId(spu.getId());
            skuMapper.delete(condition);
        }
        List<Sku> skus = goods.getSkuList();
        for (Sku sku : skus) {
            sku.setId(idWorker.nextId());
            String spec = sku.getSpec(); //json的{a:b, a:b}
            // 将json格式转化为map
            Map<String, String> map = JSON.parseObject(spec, Map.class);
            StringBuffer msg = new StringBuffer(spu.getName());
            for (String key : map.keySet()) {
                msg.append(" " + map.get(key));
            }
            sku.setName(msg.toString());
            sku.setCreateTime(new Date());
            sku.setUpdateTime(sku.getCreateTime());
            sku.setSpuId(spu.getId());
            sku.setCategoryId(spu.getCategory3Id());
            Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
            sku.setCategoryName(category.getName());
            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            sku.setBrandName(brand.getName());
            skuMapper.insertSelective(sku);
        }
    }

    @Override
    public Goods findGoodsById(Long id) {
        // 即：如何根据id找到一个goods对象，good对象包含SPU和SKU，对象
        // 实际是通过查找spu和sku然后创建一个goods对象并返沪
        Goods goods = new Goods();
        Spu spu = spuMapper.selectByPrimaryKey(id);
        Sku sku = new Sku();
        sku.setSpuId(id);
        List<Sku> skus = skuMapper.select(sku);
        goods.setSpu(spu);
        goods.setSkuList(skus);
        return goods;
    }

    @Override
    public void auditSpu(Long id) {
        //update tb_spu set status=1,is_marketable=1 where is_delete=0 and id = ?

        //先判断是否已经被删除
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (spu == null || spu.getIsDelete().equals("1")) {//已经被删除了 或者商品部存在
            throw new RuntimeException("商品不存在或者已经删除");
        }
        //审核商品
        spu.setStatus("1");//已经审核
        spu.setIsMarketable("1");//自动上架
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void pullSpu(Long id) {
        // 商品下架
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if(StringUtils.isEmpty(spu) || "1".equals(spu.getIsDelete())) {
            throw new RuntimeException("商品不存在或者已被删除");
        }
        if(!"1".equals(spu.getIsMarketable()) || !"1".equals(spu.getStatus())) {
            throw new RuntimeException("商品必须被审核或者上架状态");
        }
        spu.setIsMarketable("0");
        spuMapper.updateByPrimaryKey(spu);
    }

    @Override
    public void logicDeleteSpu(Long id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if(StringUtils.isEmpty(spu) || "1".equals(spu.getIsDelete())) {
            throw new RuntimeException("商品不存在或者已经被删除");
        }
        if(!"1".equals(spu.getIsMarketable())) {
            throw new RuntimeException("商品还未下架，不能删除");
        }
        spu.setIsDelete("1");
        spu.setStatus("0");
        spuMapper.updateByPrimaryKey(spu);
    }

    @Override
    public void restoreSpu(Long id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if(StringUtils.isEmpty(spu)){
            throw new RuntimeException("商品不存在");
        }
        Spu date = new Spu();
        date.setIsDelete("0");
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        criteria.andEqualTo("isDelete", 1);
        spuMapper.updateByExample(date, example);
    }
}
