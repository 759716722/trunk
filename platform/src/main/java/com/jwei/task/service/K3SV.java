package com.jwei.task.service;

import java.util.List;

/**
 * Created by wyb on 2018/8/16.
 */
public interface K3SV {
    public List getOvertimeProductionOrder(int timeoutDay)throws Exception;

    public List getOvertimePurchaseOrder(int timeoutDay)throws Exception;

}
