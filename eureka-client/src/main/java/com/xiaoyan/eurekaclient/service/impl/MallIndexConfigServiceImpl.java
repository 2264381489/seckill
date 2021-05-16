package com.xiaoyan.eurekaclient.service.impl;


import com.xiaoyan.eurekaclient.dao.MallGoodsIndexConfig;
import com.xiaoyan.eurekaclient.dao.MallGoodsRespository;
import com.xiaoyan.eurekaclient.entity.IndexConfig;
import com.xiaoyan.eurekaclient.entity.MallGoods;
import com.xiaoyan.eurekaclient.entity.Seckill;
import com.xiaoyan.eurekaclient.entity.ServiceResultEnum;
import com.xiaoyan.eurekaclient.service.MallIndexConfigService;
import com.xiaoyan.eurekaclient.utils.PageResult;
import com.xiaoyan.eurekaclient.vo.NewBeeMallIndexConfigGoodsVO;
import io.lettuce.core.StrAlgoArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MallIndexConfigServiceImpl implements MallIndexConfigService {

//    @Autowired
//    private IndexConfigMapper indexConfigMapper;
//
//    @Autowired
//    private NewBeeMallGoodsMapper goodsMapper;
    @Autowired
    private MallGoodsIndexConfig mallGoodsIndexConfig;
    @Autowired
    private MallGoodsRespository mallGoodsRespository;

    @Override
    public PageResult getConfigsPage(Pageable pageUtil, Byte type) {
        Page<IndexConfig> indexConfigs = mallGoodsIndexConfig.findAllByConfigType(type,pageUtil);
        return new PageResult(indexConfigs.toList(), indexConfigs.getTotalPages(), pageUtil.getPageSize(), pageUtil.getPageNumber());
    }

    @Override
    public String saveIndexConfig(IndexConfig indexConfig) {


        //todo 判断是否存在该商品
        Optional<MallGoods> temp = mallGoodsRespository.findById(indexConfig.getGoodsId());
        if (!temp.isPresent()) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        IndexConfig save = mallGoodsIndexConfig.save(indexConfig);
        if (save!=null) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateIndexConfig(IndexConfig indexConfig) {
        //todo 判断是否存在该商品
        Optional<IndexConfig> temp = mallGoodsIndexConfig.findById(indexConfig.getConfigId());
//        IndexConfig temp = indexConfigMapper.selectByPrimaryKey(indexConfig.getConfigId());
        if (!temp.isPresent()) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }

        IndexConfig save = mallGoodsIndexConfig.save(indexConfig);
        if (save!=null) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public IndexConfig getIndexConfigById(Long id) {
        IndexConfig one = mallGoodsIndexConfig.getOne(id);
        return one;
    }

    @Override
    public List<NewBeeMallIndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number) {
        return null;
    }


    @Override
    public Boolean deleteBatch(Long[] ids) {
        if (ids.length < 1) {
            return false;
        }
        List<Long> iterable= Arrays.asList(ids);
        // 确定id均存在
        List<IndexConfig> allById = mallGoodsIndexConfig.findAllById(iterable);
        //删除
        mallGoodsIndexConfig.deleteAll(allById);
        //删除数据
        return true;
    }
}
