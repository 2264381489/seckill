package com.xiaoyan.eurekaclient.service.impl;

import com.xiaoyan.eurekaclient.dao.SeckillRespository;
import com.xiaoyan.eurekaclient.dao.SuccessKilledRespository;
import com.xiaoyan.eurekaclient.dynamicquery.DynamicQuery;
import com.xiaoyan.eurekaclient.entity.Result;
import com.xiaoyan.eurekaclient.entity.Seckill;
import com.xiaoyan.eurekaclient.entity.SuccessKilled;
import com.xiaoyan.eurekaclient.enums.SeckillStatEnum;
import com.xiaoyan.eurekaclient.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Service
public class seckillServiceImpl implements SeckillService {
    @Autowired
    SeckillRespository seckillRespository;
    @Autowired
    SuccessKilledRespository successKilledRespository;
    @Autowired
    private DynamicQuery dynamicQuery;

    /**
     *  秒杀方法,有限流锁和aop锁
     * @param seckillId
     * @param userid
     * @return
     */
    //AOP锁+限流锁
    @Transactional(rollbackFor = Exception.class)
    public Result seckill(Long seckillId, Long userid) {
        String nativeSql = "SELECT number FROM seckill WHERE seckill_id=? FOR UPDATE";
        Object object = dynamicQuery.nativeQueryObject(nativeSql, new Object[]{seckillId});
        Long number = ((Number) object).longValue();
        if (number > 0) {
            insertDB(seckillId, userid);
        } else {
            return Result.error(SeckillStatEnum.END);
        }
        return Result.ok(SeckillStatEnum.SUCCESS);
    }

    /**
     * 查询总秒杀数. 验证是否超卖
     * @param seckillId
     * @return
     */
    @Override
    public Long getSeckillCount(Long seckillId) {
        Integer number = seckillRespository.findOneBySeckillId(seckillId).getNumber();

        return number.longValue();
    }

    /**
     * 插入数据库
     *
     * @param seckillId
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)// 不加这个抱这个错  Executing an update/delete query
    public void insertDB(Long seckillId, Long userId) {
        Seckill seckill = seckillRespository.findOneBySeckillId(seckillId);
        int number = seckill.getNumber();
        // 如果
        if (number > 0) {
            SuccessKilled killed = new SuccessKilled();
            killed.setSeckillId(seckillId);
            killed.setUserId(userId);
            killed.setState(Short.parseShort(number + ""));
            killed.setCreateTime(new Timestamp(System.currentTimeMillis()));
            // 存入总表 -- 进入秒杀时并不开启
            successKilledRespository.save(killed);
            String table = "success_killed_" + userId % 2;
            String nativeSql = "INSERT INTO " + table + " (seckill_id, user_id,state,create_time)VALUES(?,?,?,?)";
            Object[] params = new Object[]{seckillId, userId, (short) 0, new Timestamp(System.currentTimeMillis())};
            dynamicQuery.nativeExecuteUpdate(nativeSql, params);
        } else {
            System.out.println("已经没有货物了");
            return;
        }
    }

}
