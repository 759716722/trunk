package com.jwei.task;

import com.jwei.task.service.K3SV;
import com.jwei.utils.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by wyb on 2018/8/16.
 */
public class K3TaskMain {
    private final Logger log = LoggerFactory.getLogger(K3TaskMain.class);
    @Resource
    private K3SV k3SV;
    @Resource
    private MailService mailService;

    public void productionOvertimeNotify(){
        try {
            int timeoutDay = 3;
            log.info("超时[{}]天及以上未结案的生产任务单提醒",timeoutDay);
            List list = k3SV.getOvertimeProductionOrder(timeoutDay);
            if(list.size()<1){
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            int sumNum = list.size();
            String[] toPersons = {"j0ut254@dingtalk.com","fyqbxtu@dingtalk.com","759716722@qq.com"};
            String subject = MessageFormat.format("超时[{0}]天及以上未结案的生产任务单",timeoutDay);
            StringBuilder sb = new StringBuilder();
            sb.append("<h3>超时未结案生产任务单共[").append(sumNum).append("]条,订单如下:").append("</h3>\r\n");
            sb.append("<table border=\"1\" style=\"border-spacing:0;font-size:14px;\"><caption style=\"float:left\">订单列表</caption>")
                    .append("<tr><th>序号</th><th>订单编号</th><th>制单日期</th><th>下达日期</th><th>计划完成日期</th><th>超时天数</th><th>装配车间</th></tr>");
            for(int i=0;i<sumNum;i++){
                Map oneMap = (Map) list.get(i);
                sb.append("<tr><td>").append(i+1).append("</td><td>")
                        .append(oneMap.get("FBillNo")).append("</td><td>")
                        .append(oneMap.get("FCheckDate")==null?"":sdf.format(oneMap.get("FCheckDate"))).append("</td><td>")
                        .append(oneMap.get("FCommitDate")==null?"":sdf.format(oneMap.get("FCommitDate"))).append("</td><td>")
                        .append(oneMap.get("FPlanFinishDate")==null?"":sdf.format(oneMap.get("FPlanFinishDate"))).append("</td><td>")
                        .append(oneMap.get("timeoutDay")).append("</td><td>")
                        .append(oneMap.get("workShop")).append("</td></tr>");
            }
            sb.append("</table>");

            mailService.sendMimeMail(toPersons,null,subject,sb.toString(),null);

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();

        }
    }

    public void purchaseOvertimeNotify(){
        try {
            int timeoutDay = 3;
            log.info("超时[{}]天及以上未关闭的采购订单提醒",timeoutDay);
            List list = k3SV.getOvertimePurchaseOrder(timeoutDay);
            if(list.size()<1){
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            int sumNum = list.size();
            String[] toPersons = {"kt8f72e@dingtalk.com","fyqbxtu@dingtalk.com","759716722@qq.com"};
            String subject = MessageFormat.format("超时[{0}]天及以上未关闭的采购订单",timeoutDay);
            StringBuilder sb = new StringBuilder();
            sb.append("<h3>超时未关闭的采购订单共[").append(sumNum).append("]条,订单如下:").append("</h3>\r\n");
            sb.append("<table border=\"1\" style=\"border-spacing:0;font-size:14px;\"><caption style=\"float:left\">订单列表</caption>")
                    .append("<tr><th>序号</th><th>订单编号</th><th>制单日期</th><th>物料代码</th><th style=\"width:10%\">物料名称</th><th style=\"width:10%\">规格型号</th><th>订货数量</th><th>到货数量</th><th>入库数量</th><th>交货日期</th><th>超时天数</th><th>备注</th></tr>");
            for(int i=0;i<sumNum;i++){
                Map oneMap = (Map) list.get(i);
                sb.append("<tr><td>").append(i+1).append("</td><td>")
                        .append(oneMap.get("FBillNo")).append("</td><td>")
                        .append(oneMap.get("FDate")==null?"":sdf.format(oneMap.get("FDate"))).append("</td><td>")
                        .append(oneMap.get("FNumber")).append("</td><td>")
                        .append(oneMap.get("FName")).append("</td><td>")
                        .append(oneMap.get("FModel")).append("</td><td>")
                        .append(((BigDecimal)oneMap.get("FQty")).intValue()).append("</td><td>")
                        .append(((BigDecimal)oneMap.get("FCommitQty")).intValue()).append("</td><td>")
                        .append(((BigDecimal)oneMap.get("FStockQty")).intValue()).append("</td><td>")
                        .append(oneMap.get("deliveryDate")==null?"":sdf.format(oneMap.get("deliveryDate"))).append("</td><td>")
                        .append(oneMap.get("timeoutDay")).append("</td><td>")
                        .append(oneMap.get("FNote")).append("</td></tr>");
            }
            sb.append("</table>");

            mailService.sendMimeMail(toPersons,null,subject,sb.toString(),null);

        } catch (Exception e) {

            log.error(e.getMessage());
            e.printStackTrace();

        }
    }



}
