package com.jwei.task.service.Impl;

import com.jwei.task.dao.K3Mapper;
import com.jwei.task.service.K3SV;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wyb on 2018/8/16.
 */
@Service
public class K3Impl implements K3SV {
    @Resource
    private K3Mapper k3Mapper;
    @Override
    public List getOvertimeProductionOrder(int timeoutDay) throws Exception {
        return k3Mapper.selectProductionOrder(timeoutDay);
    }

    @Override
    public List getOvertimePurchaseOrder(int timeoutDay) throws Exception {
        return k3Mapper.selectPurchaseOrder(timeoutDay);
    }
}
