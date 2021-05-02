package com.xiaoyan.eurekaclient.service;

import com.xiaoyan.eurekaclient.entity.Result;

public interface SeckillService {

// 存入数据库的步骤
    public void insertDB(Long seckillId,Long userid);

    public Result seckill(Long seckillId, Long userid);

    Long getSeckillCount(Long seckillId);


}
