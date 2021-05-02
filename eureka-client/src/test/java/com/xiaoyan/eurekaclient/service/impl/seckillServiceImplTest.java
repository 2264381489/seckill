package com.xiaoyan.eurekaclient.service.impl;

import com.xiaoyan.eurekaclient.entity.Result;
import com.xiaoyan.eurekaclient.service.SeckillService;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@RunWith(SpringRunner.class)
@SpringBootTest
class seckillServiceImplTest {
    @Autowired
    SeckillService seckillService;

    @Test
    void insertDB() {
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            seckillService.seckill(1004L, Math.abs(random.nextLong())
            );
        }
    }



}