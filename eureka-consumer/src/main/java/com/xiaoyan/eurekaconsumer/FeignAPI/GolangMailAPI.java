package com.xiaoyan.eurekaconsumer.FeignAPI;

import com.xiaoyan.eurekaconsumer.entity.emailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("eureka-golang")
public interface GolangMailAPI{
    @GetMapping("/seckill")
    String consumer(@RequestParam("EmailReq") emailDTO emailReq);
}
