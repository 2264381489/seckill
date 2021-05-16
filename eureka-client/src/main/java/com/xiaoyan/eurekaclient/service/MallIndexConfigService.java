package com.xiaoyan.eurekaclient.service;


import com.xiaoyan.eurekaclient.entity.IndexConfig;
import com.xiaoyan.eurekaclient.utils.PageResult;
import com.xiaoyan.eurekaclient.vo.NewBeeMallIndexConfigGoodsVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MallIndexConfigService {
    /**
     * 后台分页
     *
     * @param
     * @return
     */
    PageResult getConfigsPage(Pageable pageUtil,Byte configType);

    String saveIndexConfig(IndexConfig indexConfig);

    String updateIndexConfig(IndexConfig indexConfig);

    IndexConfig getIndexConfigById(Long id);

    /**
     * 返回固定数量的首页配置商品对象(首页调用)
     *
     * @param number
     * @return
     */
    List<NewBeeMallIndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number);

    Boolean deleteBatch(Long[] ids);
}
