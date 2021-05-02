package com.xiaoyan.eurekaclient.Controller;

import com.xiaoyan.eurekaclient.dao.TestJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testDB {

    @Autowired
    TestJPA testJPA;
    @RequestMapping("/testDB")
    public String testDB(){
       return testJPA.test();

    }


}
