package com.xiaoyan.eurekaclient.dao;

import com.xiaoyan.eurekaclient.entity.IndexConfig;
import com.xiaoyan.eurekaclient.entity.MallGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MallGoodsRespository extends JpaRepository<MallGoods,Long>, CrudRepository<MallGoods,Long> {


}
