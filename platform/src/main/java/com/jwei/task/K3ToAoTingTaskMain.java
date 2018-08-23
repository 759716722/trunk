package com.jwei.task;

import com.alibaba.druid.support.json.JSONUtils;
import com.jwei.task.dao.K3Mapper;
import com.jwei.task.service.K3ToAoTingSV;
import com.jwei.utils.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wyb on 2018/7/23.
 */
public class K3ToAoTingTaskMain {
    private final Logger log = LoggerFactory.getLogger(K3ToAoTingTaskMain.class);

    @Resource
    private K3Mapper k3Mapper;
    @Resource
    private K3ToAoTingSV k3ToAoTingSV;
    @Resource
    private MailService mailService;

    public void doK3ToAoTing() {
        List<String> errorList = new ArrayList();
        List jwOrderList = k3Mapper.selectSaleOrder();
        int sumNum = jwOrderList.size();
        log.info("task任务检测到K3易图账套需要处理[{}]条订单记录", sumNum);
        for (int i = 0; i < sumNum ; i++) {
            String jwFlag = "990136";      // 易图账套 已同步 id
            Map jwOrderMap = (Map) jwOrderList.get(i);
            String aotingFlag = jwOrderMap.get("aotingFlag").toString();
            String orderId = jwOrderMap.get("orderId").toString();
            String orderNo = jwOrderMap.get("orderNo").toString();
            try {
                if ("1".equals(aotingFlag)) {
                    log.info("处理K3易图账套ID[{}],编号[{}]的订单,处理类型[新增]", orderId, orderNo);
                    k3ToAoTingSV.createSaleOrderInfo(orderId);

                } else if ("2".equals(aotingFlag)) {
                    log.info("处理K3易图账套ID[{}],编号[{}]的订单,处理类型[修改]", orderId, orderNo);
                    k3ToAoTingSV.updateSaleOrderInfo(orderId);
                }
                log.info("K3易图账套ID[{}],编号[{}]的订单处理完成。", orderId, orderNo);

            } catch (Exception e) {
                jwFlag = "0";  // 处理异常状态
                errorList.add("K3易图账套订单编号[" + orderNo + "]同步失败，原因如下："+e.getMessage());
                log.error(e.getMessage());
                e.printStackTrace();

            } finally {
                k3Mapper.updateOrderByOrderId(orderId, jwFlag);
            }
        }
        if(errorList.size()>0){
            this.k3ToATMailNotify(sumNum,errorList);
        }

    }

    public void k3ToATMailNotify(int sumNum,List errorList) {

        try {
            String[] toPersons = {"759716722@qq.com","ben0040@dingtalk.com","xszl02@edocut.com"};
            String subject = "K3易图账套同步奥汀通知";
            StringBuilder sb = new StringBuilder();
            sb.append("总共需要同步[").append(sumNum).append("]条记录,已成功同步[")
                    .append(sumNum-errorList.size()).append("]条记录,未能成功同步[")
                    .append(errorList.size()).append("]条记录。");
            if(errorList.size()>0){
                sb.append("错误信息如下：").append("\r\n");
                sb.append(JSONUtils.toJSONString(errorList));
            }
            mailService.sendSimpleMail(toPersons,null,subject,sb.toString());

        } catch (Exception e) {

            log.error(e.getMessage());
            e.printStackTrace();

        }
    }






}
