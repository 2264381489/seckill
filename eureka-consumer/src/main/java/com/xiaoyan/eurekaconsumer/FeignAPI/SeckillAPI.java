package com.xiaoyan.eurekaconsumer.FeignAPI;

import com.xiaoyan.eurekaconsumer.entity.emailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("eureka-client")
public interface SeckillAPI {
    //：当使用Feign时，如果发送的是get请求，那么需要在请求参数前加上@RequestParam注解修饰，Controller里面可以不加该注解修饰。
    //否则会报Caused by: java.lang.IllegalStateException: Method has too many Body parameters:
    @GetMapping("/seckill")
    String consumer(@RequestParam("seckillId")Long seckillId, @RequestParam("userid") Long userid);

}


