package com.jwei.task.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface K3Mapper {

    List selectSaleOrder();

    Map selectYTByOrderId(String orderId);

    /**
     * 描述: 默认第一行为产品，其他为配件
     * @author: wyb
     * @date: 2018/7/28
     */
    List selectDetailByOrderId(String orderId);

    int updateOrderByOrderId(@Param("orderId")String orderId,@Param("state")String state);

    /**
     * 描述: 查超时计划完成日期3天未结案的生产任务单
     * @author: wyb
     * @date: 2018/8/16
     */
    List selectProductionOrder(int timeoutDay);

    /**
     * 描述: 查超时交货日期3天未关闭的采购订单
     * @author: wyb
     * @date: 2018/8/17
     */
    List selectPurchaseOrder(int timeoutDay);

}