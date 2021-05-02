package com.xiaoyan.eurekaclient.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.xiaoyan.eurekaclient.entity.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SeckillRespository extends JpaRepository<Seckill,Long>,CrudRepository<Seckill,Long>  {

    public Seckill findOneBySeckillId(Long seckillId);
}
