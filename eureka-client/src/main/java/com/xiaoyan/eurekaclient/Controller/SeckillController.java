package com.xiaoyan.eurekaclient.Controller;

import com.xiaoyan.eurekaclient.entity.Result;
import com.xiaoyan.eurekaclient.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeckillController {

@Autowired
    SeckillService seckillService;

    /**
     * 用来调用的接口
     * @param seckillId
     * @param userid
     * @return
     */
    @GetMapping("/seckill")
    public Result seckill(Long seckillId, Long userid) {
        return seckillService.seckill(seckillId,userid);
    }

}
