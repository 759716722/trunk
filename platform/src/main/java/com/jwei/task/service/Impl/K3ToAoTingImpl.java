package com.jwei.task.service.Impl;

import com.jwei.task.dao.ATMapper;
import com.jwei.task.dao.K3Mapper;
import com.jwei.task.service.K3ToAoTingSV;
import com.jwei.utils.ExceptionAPI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by wyb on 2018/7/26.
 */
@Service
public class K3ToAoTingImpl implements K3ToAoTingSV {

    @Resource
    private ATMapper atMapper;
    @Resource
    private K3Mapper k3Mapper;

    public void createSaleOrderInfo(String orderId) throws Exception{
        Map jwOrderMap = k3Mapper.selectYTByOrderId(orderId);
        List jwOrderDetailList = k3Mapper.selectDetailByOrderId(orderId);
        String orderNo = jwOrderMap.get("orderNo").toString();
        Map atOrderMap = atMapper.selectOrderByOrderNo(orderNo);

        if(atOrderMap==null){
            // 构造订单信息
            atOrderMap = new HashMap();
            String atOrderId=UUID.randomUUID().toString().replace("-", "");
            atOrderMap.put("id",atOrderId);
            atOrderMap.put("subject",jwOrderMap.get("subject"));
            atOrderMap.put("code",orderNo);
            Integer sort = atMapper.selectOrderMaxSort();
            atOrderMap.put("sort",sort);
            Integer own_dpt = null;
            Integer own_usr = null;
            String createName = (String) jwOrderMap.get("createName");
            Map atUserMap = atMapper.selectUserByName(createName);
            if(atUserMap==null){  //如果为查找不到 则默认为内贸部/孙星创建
                own_dpt = 11;
                own_usr = 15;
            }else{
                own_dpt = (Integer) atUserMap.get("depart_id");
                own_usr = (Integer) atUserMap.get("id");
            }
            atOrderMap.put("own_dpt",own_dpt);
            atOrderMap.put("own_usr",own_usr);

            atOrderMap.put("custom19",jwOrderMap.get("createName"));
            atOrderMap.put("sell_date",jwOrderMap.get("orderDate"));
            atOrderMap.put("custom21",jwOrderMap.get("sendDate"));
            atOrderMap.put("seller",jwOrderMap.get("saleName"));

            atOrderMap.put("custom41",jwOrderMap.get("saleChannel"));
            atOrderMap.put("custom2",jwOrderMap.get("custUseSoft"));
            atOrderMap.put("custom23",jwOrderMap.get("controlCent"));
            atOrderMap.put("custom1",jwOrderMap.get("receiver")+"/"+jwOrderMap.get("linkman"));
            atOrderMap.put("phone",jwOrderMap.get("linkphone"));
            atOrderMap.put("address",jwOrderMap.get("sendAdd"));
            atOrderMap.put("custom24",jwOrderMap.get("payStyle"));
            atOrderMap.put("custom25",jwOrderMap.get("service"));
            atOrderMap.put("custom26",jwOrderMap.get("OEM"));
            atOrderMap.put("custom27",jwOrderMap.get("guarantee"));
            atOrderMap.put("custom40",jwOrderMap.get("boxConfig"));
            atOrderMap.put("custom29",jwOrderMap.get("powerLine"));
            atOrderMap.put("custom31",jwOrderMap.get("box"));
            atOrderMap.put("custom30",jwOrderMap.get("printLine"));
            atOrderMap.put("custom32",jwOrderMap.get("noteBook"));
            atOrderMap.put("custom33",jwOrderMap.get("pack"));
            atOrderMap.put("custom34",jwOrderMap.get("CE"));
            atOrderMap.put("custom35",jwOrderMap.get("fumigate"));
            atOrderMap.put("custom36",jwOrderMap.get("mark"));
            atOrderMap.put("custom37",jwOrderMap.get("correctionNote"));
            atOrderMap.put("custom38",jwOrderMap.get("tableTop"));
            atOrderMap.put("custom39",jwOrderMap.get("setPassword"));
            atOrderMap.put("custom15",jwOrderMap.get("gift"));
            atOrderMap.put("custom16",null);  // 备注
            atOrderMap.put("remark",jwOrderMap.get("remark"));  // 补充备注
            atOrderMap.put("custom17",jwOrderMap.get("deptName"));
            atOrderMap.put("custom42",jwOrderMap.get("industryName"));

            String custName = (String)jwOrderMap.get("custName"); //代理商/客户  可能为null 不能 toString()
            if(StringUtils.isBlank(custName)){
                custName = "";
            }
            String endCust = (String)jwOrderMap.get("endCust");  //终端客户  可能为null 不能 toString()
            if(StringUtils.isNotBlank(endCust)){
                custName = custName+"["+endCust+"]";
            }
            if(StringUtils.isBlank(custName)){
                throw new ExceptionAPI("K3易图账套ID["+orderId+"],编号["+orderNo+"]的订单客户信息为空，请核查。");
            }
            // 客户名称的生成方式：客户名称 + 数据库中模糊查询的记录数
            List atCustomerList = atMapper.selectCustomerByName(custName);
            if(atCustomerList.size()>0){
                custName = custName+atCustomerList.size();
            }
            // 构造客户信息
            Map atCustMap = new HashMap();
            String atCustId = UUID.randomUUID().toString().replace("-", "");
            atCustMap.put("id",atCustId);
            atCustMap.put("name",custName);
            atCustMap.put("code",null);   // 编码
            atCustMap.put("spellcode",null);   // 拼音码
            atCustMap.put("first_buy_date",jwOrderMap.get("orderDate")); // 首次购买时间
            atCustMap.put("custom6",jwOrderMap.get("sendDate"));       //  发货日期
            atCustMap.put("region",jwOrderMap.get("deptName"));       //  所属区域
            atCustMap.put("trade",jwOrderMap.get("industryName"));       //   所属行业
            atCustMap.put("custom7",orderNo);       // 订单编号
            Map tempDetailMap = (Map) jwOrderDetailList.get(0);
            atCustMap.put("custom3",tempDetailMap.get("productName"));       // 机器型号
            atCustMap.put("telephone",jwOrderMap.get("receiver")+"/"+jwOrderMap.get("linkman")+jwOrderMap.get("linkphone"));       // 联系人和电话
            atCustMap.put("custom20",jwOrderMap.get("saleName"));       // 销售员
            atCustMap.put("address",jwOrderMap.get("sendAdd"));       // 地址
            atCustMap.put("remark",jwOrderMap.get("remark"));       // 备注

            atCustMap.put("own_dpt",own_dpt);                  // 所属部门
            atCustMap.put("own_usr",own_usr);                 // 所属人

            atMapper.insertCustomer(atCustMap);
            atOrderMap.put("account_id",atCustId);

            atMapper.insertOrder(atOrderMap);

            // 构造订单明细
            int orderDetailSize = jwOrderDetailList.size();
            for(int i=0;i<orderDetailSize;i++){
                Map jwOrderDetailMap = (Map) jwOrderDetailList.get(i);
                Map atOrderDetailMap = new HashMap();
                atOrderDetailMap.put("id",UUID.randomUUID().toString().replace("-", ""));
                atOrderDetailMap.put("sort",orderDetailSize-i);
                atOrderDetailMap.put("sale_order_id",atOrderId);
                if(i==0){
                    atOrderDetailMap.put("catalog_id","8a8190bd3274ec0c01328124851c03d6"); // 成品机ID
                }else{
                    atOrderDetailMap.put("catalog_id","8a8190bd3274ec0c01328124ea0003d8"); // 配件ID
                }
                atOrderDetailMap.put("quantity",jwOrderDetailMap.get("productNum")); // 数量
                atOrderDetailMap.put("custom8",jwOrderDetailMap.get("airPumpModel")); // 气泵型号
                atOrderDetailMap.put("custom4",jwOrderDetailMap.get("airPumpNum")+"/"+jwOrderDetailMap.get("airPumpModel")); // 气泵数量
                atOrderDetailMap.put("spec",jwOrderDetailMap.get("productCode"));       // 规格 用来存放易图配件编码
                String productGeneralDesc = (String) jwOrderDetailMap.get("productGeneralDesc");
                String productSpecialDesc = (String) jwOrderDetailMap.get("productSpecialDesc");
                if(StringUtils.isBlank(productGeneralDesc)){
                    atOrderDetailMap.put("custom16","无"); // 常规说明
                }else{
                    atOrderDetailMap.put("custom16",productGeneralDesc); // 常规说明
                }
                if(StringUtils.isBlank(productSpecialDesc)){
                    atOrderDetailMap.put("custom7","无"); // 常规说明
                }else{
                    atOrderDetailMap.put("custom7",productSpecialDesc); // 常规说明
                }
                atOrderDetailMap.put("custom5","无"); // 购买软件名称
                atOrderDetailMap.put("custom6","无"); // 用户数

                String jwProductName = jwOrderDetailMap.get("productName").toString();
                Map atProductMap = atMapper.selectProductByName(jwProductName);
                if(atProductMap==null){   //如果没有该型号，则新增
                    // 构造产品信息
                    atProductMap = new HashMap();
                    String atProductId = UUID.randomUUID().toString().replace("-", "");
                    atProductMap.put("id",atProductId);
                    atProductMap.put("name",jwProductName);
                    atProductMap.put("code",null);   // 产品编码
                    if(i==0){
                        atProductMap.put("catalog_id","8a8190bd3274ec0c01328124851c03d6"); // 成品机ID
                    }else{
                        atProductMap.put("catalog_id","8a8190bd3274ec0c01328124ea0003d8"); // 配件ID
                    }
                    atProductMap.put("spellcode",null); // 拼音码

                    atMapper.insertProduct(atProductMap);

                    atOrderDetailMap.put("product_id",atProductId);  // 将产品id 赋值订单明细

                }else{   // 有 则将ID赋值
                    atOrderDetailMap.put("product_id",atProductMap.get("id"));  // 将产品id 赋值订单明细
                }

                atMapper.insertOrderDetail(atOrderDetailMap);
            }

        }else{
            throw new ExceptionAPI("K3易图账套ID["+orderId+"],编号["+orderNo+"]的订单在奥汀里已存在，请核查。");
        }

    }


    public void updateSaleOrderInfo(String orderId) throws Exception{
        Map jwOrderMap = k3Mapper.selectYTByOrderId(orderId);
        List jwOrderDetailList = k3Mapper.selectDetailByOrderId(orderId);
        String orderNo = jwOrderMap.get("orderNo").toString();
        Map atOrderMap = atMapper.selectOrderByOrderNo(orderNo);

        if(atOrderMap!=null){
            // 构造订单信息
            atOrderMap.put("subject",jwOrderMap.get("subject"));
            atOrderMap.put("custom19",jwOrderMap.get("createName"));
            atOrderMap.put("sell_date",jwOrderMap.get("orderDate"));
            atOrderMap.put("custom21",jwOrderMap.get("sendDate"));
            atOrderMap.put("seller",jwOrderMap.get("saleName"));
            atOrderMap.put("custom41",jwOrderMap.get("saleChannel"));
            atOrderMap.put("custom2",jwOrderMap.get("custUseSoft"));
            atOrderMap.put("custom23",jwOrderMap.get("controlCent"));
            atOrderMap.put("custom1",jwOrderMap.get("receiver")+"/"+jwOrderMap.get("linkman"));
            atOrderMap.put("phone",jwOrderMap.get("linkphone"));
            atOrderMap.put("address",jwOrderMap.get("sendAdd"));
            atOrderMap.put("custom24",jwOrderMap.get("payStyle"));
            atOrderMap.put("custom25",jwOrderMap.get("service"));
            atOrderMap.put("custom26",jwOrderMap.get("OEM"));
            atOrderMap.put("custom27",jwOrderMap.get("guarantee"));
            atOrderMap.put("custom40",jwOrderMap.get("boxConfig"));
            atOrderMap.put("custom29",jwOrderMap.get("powerLine"));
            atOrderMap.put("custom31",jwOrderMap.get("box"));
            atOrderMap.put("custom30",jwOrderMap.get("printLine"));
            atOrderMap.put("custom32",jwOrderMap.get("noteBook"));
            atOrderMap.put("custom33",jwOrderMap.get("pack"));
            atOrderMap.put("custom34",jwOrderMap.get("CE"));
            atOrderMap.put("custom35",jwOrderMap.get("fumigate"));
            atOrderMap.put("custom36",jwOrderMap.get("mark"));
            atOrderMap.put("custom37",jwOrderMap.get("correctionNote"));
            atOrderMap.put("custom38",jwOrderMap.get("tableTop"));
            atOrderMap.put("custom39",jwOrderMap.get("setPassword"));
            atOrderMap.put("custom15",jwOrderMap.get("gift"));
            atOrderMap.put("custom16",null);  // 备注
            atOrderMap.put("remark",jwOrderMap.get("remark"));  // 补充备注
            atOrderMap.put("custom17",jwOrderMap.get("deptName"));
            atOrderMap.put("custom42",jwOrderMap.get("industryName"));

            // 构造客户信息 用于更新
            Map atCustMap = new HashMap<>();
            String accountId = atOrderMap.get("account_id").toString();

            String custName = (String)jwOrderMap.get("custName");
            if(StringUtils.isBlank(custName)){
                custName = "";
            }
            String endCust = (String)jwOrderMap.get("endCust");  //终端客户  可能为null 不能 toString()
            if(StringUtils.isNotBlank(endCust)){
                custName = custName+"["+endCust+"]";
            }
            if(StringUtils.isBlank(custName)){
                throw new ExceptionAPI("K3易图账套ID["+orderId+"],编号["+orderNo+"]的订单客户信息为空，请核查。");
            }
            // 判断客户名称是否有所更改
            Map oldAtCustomer = atMapper.selectCustomerById(accountId);
            String oldCustName = oldAtCustomer.get("name").toString();

            // 如果更改，则重新按照规则生产命名并更新该记录
            if(!oldCustName.contains(custName)){
                // 客户名称的生成方式：客户名称 + 数据库中模糊查询的记录数
                List atCustomerList = atMapper.selectCustomerByName(custName);
                if(atCustomerList.size()>0){
                    custName = custName+atCustomerList.size();
                }
                atCustMap.put("name",custName);
            }

            atCustMap.put("id",accountId);
            atCustMap.put("code",null);   // 编码
            atCustMap.put("spellcode",null);   // 拼音码
            atCustMap.put("first_buy_date",jwOrderMap.get("orderDate")); // 首次购买时间
            atCustMap.put("custom6",jwOrderMap.get("sendDate"));       //  发货日期
            atCustMap.put("region",jwOrderMap.get("deptName"));       //  所属区域
            atCustMap.put("trade",jwOrderMap.get("industryName"));       //   所属行业
            atCustMap.put("custom7",orderNo);                           // 订单编号
            Map tempDetailMap = (Map) jwOrderDetailList.get(0);
            atCustMap.put("custom3",tempDetailMap.get("productName"));       // 机器型号
            atCustMap.put("telephone",jwOrderMap.get("receiver")+"/"+jwOrderMap.get("linkman")+jwOrderMap.get("linkphone"));       // 联系人和电话
            atCustMap.put("custom20",jwOrderMap.get("saleName"));       // 销售员
            atCustMap.put("address",jwOrderMap.get("sendAdd"));       // 地址
            atCustMap.put("remark",jwOrderMap.get("remark"));       // 备注

            atMapper.updateCustomerByPrimaryKeySelective(atCustMap);

            atMapper.updateOrderByPrimaryKeySelective(atOrderMap);

            String atOrderId = atOrderMap.get("id").toString();

            // 先删除老记录
            atMapper.deleteOrderDetailByOrderId(atOrderId);
            // 构造订单明细
            int orderDetailSize = jwOrderDetailList.size();
            for(int i=0;i<orderDetailSize;i++){
                Map jwOrderDetailMap = (Map) jwOrderDetailList.get(i);
                Map atOrderDetailMap = new HashMap();
                atOrderDetailMap.put("id",UUID.randomUUID().toString().replace("-", ""));
                atOrderDetailMap.put("sort",orderDetailSize-i);
                atOrderDetailMap.put("sale_order_id",atOrderId);
                if(i==0){
                    atOrderDetailMap.put("catalog_id","8a8190bd3274ec0c01328124851c03d6"); // 成品机ID
                }else{
                    atOrderDetailMap.put("catalog_id","8a8190bd3274ec0c01328124ea0003d8"); // 配件ID
                }
                atOrderDetailMap.put("quantity",jwOrderDetailMap.get("productNum")); // 机器数量
                atOrderDetailMap.put("custom8",jwOrderDetailMap.get("airPumpModel")); // 气泵型号
                atOrderDetailMap.put("custom4",jwOrderDetailMap.get("airPumpNum")+"/"+jwOrderDetailMap.get("airPumpModel")); // 气泵数量
                atOrderDetailMap.put("spec",jwOrderDetailMap.get("productCode"));       // 规格 用来存放易图配件编码
                String productGeneralDesc = (String) jwOrderDetailMap.get("productGeneralDesc");
                String productSpecialDesc = (String) jwOrderDetailMap.get("productSpecialDesc");
                if(StringUtils.isBlank(productGeneralDesc)){
                    atOrderDetailMap.put("custom16","无"); // 常规说明
                }else{
                    atOrderDetailMap.put("custom16",productGeneralDesc); // 常规说明
                }
                if(StringUtils.isBlank(productSpecialDesc)){
                    atOrderDetailMap.put("custom7","无"); // 常规说明
                }else{
                    atOrderDetailMap.put("custom7",productSpecialDesc); // 常规说明
                }
                atOrderDetailMap.put("custom5","无"); // 购买软件名称
                atOrderDetailMap.put("custom6","无"); // 用户数

                String jwProductName = jwOrderDetailMap.get("productName").toString();
                Map atProductMap = atMapper.selectProductByName(jwProductName);
                if(atProductMap==null){   //如果没有该型号，则新增
                    // 构造产品信息
                    atProductMap = new HashMap();
                    String atProductId = UUID.randomUUID().toString().replace("-", "");
                    atProductMap.put("id",atProductId);
                    atProductMap.put("name",jwProductName);
                    atProductMap.put("code",null);   // 产品编码
                    if(i==0){
                        atProductMap.put("catalog_id","8a8190bd3274ec0c01328124851c03d6"); // 成品机ID
                    }else{
                        atProductMap.put("catalog_id","8a8190bd3274ec0c01328124ea0003d8"); // 配件ID
                    }
                    atProductMap.put("spellcode",null); // 拼音码

                    atMapper.insertProduct(atProductMap);

                    atOrderDetailMap.put("product_id",atProductId);  // 将产品id 赋值订单明细

                }else{   // 有 则将ID赋值
                    atOrderDetailMap.put("product_id",atProductMap.get("id"));  // 将产品id 赋值订单明细
                }

                atMapper.insertOrderDetail(atOrderDetailMap);
            }

        }else{
            throw new ExceptionAPI("K3易图账套ID为["+orderId+"],编号["+orderNo+"]的订单在奥汀里不存在，请核查。");
        }
    }


}
