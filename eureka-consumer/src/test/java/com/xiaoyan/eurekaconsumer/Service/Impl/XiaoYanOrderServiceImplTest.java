package com.xiaoyan.eurekaconsumer.Service.Impl;

import com.xiaoyan.eurekaconsumer.Service.XiaoYanOrderService;
import com.xiaoyan.eurekaconsumer.entity.NewBeeMallOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XiaoYanOrderServiceImplTest {
    @Autowired
    XiaoYanOrderService xiaoYanOrderService;

    @Test
  public void Run() {
        Page<NewBeeMallOrder> newBeeMallOrderList = xiaoYanOrderService.findNewBeeMallOrderList(0, 10);
        int totalPages = newBeeMallOrderList.getTotalPages();
        System.out.println("一共有多少页");
        System.out.println(totalPages);
    }
}