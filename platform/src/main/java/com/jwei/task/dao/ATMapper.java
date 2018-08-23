package com.jwei.task.dao;

import com.jwei.sys.entity.SysDept;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ATMapper {


    Map selectUserByName(String name);

    Map selectCustomerById(String id);

    List selectCustomerByName(@Param("custName") String custName);

    int insertCustomer(Map custMap);

    int updateCustomerByPrimaryKeySelective(Map custMap);

    Map selectProductByName(String productName);

    int insertProduct(Map productMap);

    Map selectOrderByOrderNo(String orderNo);

    Integer selectOrderMaxSort();

    int insertOrder(Map orderMap);

    int updateOrderByPrimaryKeySelective(Map orderMap);

    int insertOrderDetail(Map orderDetailMap);

    int updateOrderDetailByPrimaryKeySelective(Map orderDetailMap);

    int deleteOrderDetailByOrderId(String orderId);


}