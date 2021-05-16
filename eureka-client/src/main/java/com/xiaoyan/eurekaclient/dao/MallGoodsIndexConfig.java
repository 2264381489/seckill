package com.xiaoyan.eurekaclient.dao;

import com.xiaoyan.eurekaclient.entity.IndexConfig;
import com.xiaoyan.eurekaclient.entity.Seckill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MallGoodsIndexConfig extends JpaRepository<IndexConfig,Long>, CrudRepository<IndexConfig,Long> {

    Page<IndexConfig> findAllByConfigType(Byte configType,Pageable pageable);

}
