package com.xiaoyan.eurekaclient.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.xiaoyan.eurekaclient.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class TestJPA {
@Autowired
SeckillRespository seckillRespository;

    public String test(){
        List<Seckill> all = seckillRespository.findAll();
       Seckill seckill = all.get(0);
        System.out.println(seckill.getName());
    return seckill.getName();
    }

}
