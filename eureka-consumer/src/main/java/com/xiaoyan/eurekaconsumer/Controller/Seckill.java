package com.xiaoyan.eurekaconsumer.Controller;


import com.xiaoyan.eurekaconsumer.FeignAPI.SeckillAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Seckill {

    @Autowired
    SeckillAPI Client;
//：当使用Feign时，如果发送的是get请求，那么需要在请求参数前加上@RequestParam注解修饰，Controller里面可以不加该注解修饰。
    //否则会报Caused by: java.lang.IllegalStateException: Method has too many Body parameters:
    @GetMapping("/consumer")
    public String dc(@RequestParam("seckillId")Long seckillId, @RequestParam("userid") Long userid) {
        return Client.consumer(seckillId, userid);
    }



}
