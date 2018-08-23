package com.jwei.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyb on 2018/7/31.
 */
public class JVMInfo {

    public Map getJVMInfo(){

        Runtime runtimeInfo = Runtime.getRuntime();
        long totalMemory = runtimeInfo.totalMemory();          // Java虚拟机的总内存
        long maxMemory = runtimeInfo.maxMemory();             // 最大内存
        long freeMemory = runtimeInfo.freeMemory();          // 可用内存
        int cpuNum =runtimeInfo.availableProcessors();      // 处理器数量

        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        int MB = 1024 * 1024;
        System.out.println("总内存"+df.format(totalMemory/MB) + "MB");
        System.out.println("最大内存"+df.format(maxMemory/MB) + "MB");
        System.out.println("可用内存"+df.format(freeMemory/MB) + "MB");
        System.out.println("处理器数量"+cpuNum);
        Map resultMap = new HashMap();
        resultMap.put("totalMemory",df.format(totalMemory/MB) + "MB");
        resultMap.put("maxMemory",df.format(maxMemory/MB) + "MB");
        resultMap.put("freeMemory",df.format(freeMemory/MB) + "MB");
        resultMap.put("cpuNum",cpuNum);
        return resultMap;
    }

}
