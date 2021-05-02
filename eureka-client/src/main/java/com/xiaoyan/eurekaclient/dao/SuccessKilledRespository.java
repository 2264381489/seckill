package com.xiaoyan.eurekaclient.dao;

import com.xiaoyan.eurekaclient.entity.Seckill;
import com.xiaoyan.eurekaclient.entity.SuccessKilled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface SuccessKilledRespository  extends JpaRepository<SuccessKilled,Long>, CrudRepository<SuccessKilled,Long> {
}
