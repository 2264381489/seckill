package com.xiaoyan.eurekaconsumer.Dao;

import com.xiaoyan.eurekaconsumer.entity.NewBeeMallOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface MallOrderRespository extends JpaRepository<NewBeeMallOrder,Long>,CrudRepository<NewBeeMallOrder,Long> , PagingAndSortingRepository<NewBeeMallOrder, Long> , QuerydslPredicateExecutor<NewBeeMallOrder> {

}
