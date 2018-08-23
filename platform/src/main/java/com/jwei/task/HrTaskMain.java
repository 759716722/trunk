package com.jwei.task;

import com.alibaba.druid.support.json.JSONUtils;
import com.jwei.hr.entity.HrEmployee;
import com.jwei.hr.service.EmployeeSV;
import com.jwei.utils.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wyb on 2018/7/23.
 */
public class HrTaskMain {
    private final Logger log = LoggerFactory.getLogger(HrTaskMain.class);

    final String[] toPersons={"759716722@qq.com","secretary@jingwei.com.cn",};

    @Resource
    private EmployeeSV employeeSV;
    @Resource
    private MailService mailService;

    public void doBirthdayNotify() {
        Calendar calendar = Calendar.getInstance();
        int currMonth = calendar.get(Calendar.MONTH)+2; // 查询下月
        log.info("{}月份员工生日提醒",currMonth);

        try {
            List<HrEmployee> employeeList = employeeSV.getEmployeeByCond(currMonth,null,null,Byte.valueOf("1"));
            StringBuilder sb = new StringBuilder();
            if(employeeList.size()>0){
                sb.append("<h3>"+currMonth+"月份共[").append(employeeList.size()).append("]位员工生日,名单如下:").append("</h3>\r\n");
                sb.append("<table border=\"1\" style=\"width:80%;border-spacing:0;\"><caption style=\"float:left\">员工列表</caption>")
                        .append("<tr><th>姓名</th><th>部门</th><th>职务</th><th>手机号码</th><th>生日</th><th>入职日期</th></tr>");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for(HrEmployee one:employeeList){
                    sb.append("<tr><td>"+one.getName()+"</td><td>"+one.getDeptName()+"</td><td>"+one.getDuty()+"</td><td>"+one.getPhone()+"</td><td>"+sdf.format(one.getBirthday())+"</td><td>"+sdf.format(one.getJoinDate())+"</td></tr>");
                }
                sb.append("</table>");
            }else{
                sb.append("<h3>"+currMonth+"月份共[").append(employeeList.size()).append("]位员工生日。").append("</h3>\r\n");
            }

            String subject = currMonth+"月份员工生日提醒";
            mailService.sendMimeMail(toPersons,null,subject,sb.toString(),null);

        }catch (Exception e) {

            log.error(e.getMessage());
            e.printStackTrace();

        } finally {

        }

    }

    public void doContractNotify() {
        int contractEndDay = 45,noContractDay = 20;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,contractEndDay);
        Date contractEndDate = calendar.getTime();
        log.info("未签劳动合同/劳动合同到期提醒");

        try {
            List<HrEmployee> contractEndList = employeeSV.getEmployeeByCond(null,null,contractEndDate,Byte.valueOf("1"));
            List<HrEmployee> noContractList = employeeSV.getEmployeeByCond(null,noContractDay,null,Byte.valueOf("1"));

            StringBuilder sb_end = new StringBuilder();
            if(contractEndList.size()>0){
                sb_end.append("<h3>"+contractEndDay+"天内合同到期的员工共[").append(contractEndList.size()).append("]位,名单如下:").append("</h3>\r\n");
                sb_end.append("<table border=\"1\" style=\"width:80%;border-spacing:0;\"><caption style=\"float:left\">员工列表</caption>")
                        .append("<tr><th>姓名</th><th>部门</th><th>职务</th><th>手机号码</th><th>合同到期</th><th>入职日期</th></tr>");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for(HrEmployee one:contractEndList){
                    sb_end.append("<tr><td>"+one.getName()+"</td><td>"+one.getDeptName()+"</td><td>"+one.getDuty()+"</td><td>"+one.getPhone()+"</td><td>"+sdf.format(one.getContractEndDate())+"</td><td>"+sdf.format(one.getJoinDate())+"</td></tr>");
                }
                sb_end.append("</table>\n");
            }else{
                sb_end.append("<h3>"+contractEndDay+"天内合同到期的员工共[").append(contractEndList.size()).append("]位。").append("</h3>\r\n");
            }

            StringBuilder sb_no = new StringBuilder();
            if(noContractList.size()>0){
                sb_no.append("<h3>入职"+noContractDay+"天以上未签订合同的员工共[").append(contractEndList.size()).append("]位,名单如下:").append("</h3>\r\n");
                sb_no.append("<table border=\"1\" style=\"width:80%;border-spacing:0;\"><caption style=\"float:left\">员工列表</caption>\n")
                        .append("<tr><th>姓名</th><th>部门</th><th>职务</th><th>手机号码</th><th>入职日期</th></tr>");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for(HrEmployee one:noContractList){
                    sb_no.append("<tr><td>"+one.getName()+"</td><td>"+one.getDeptName()+"</td><td>"+one.getDuty()+"</td><td>"+one.getPhone()+"</td><td>"+sdf.format(one.getJoinDate())+"</td></tr>");
                }
                sb_no.append("</table>\n");
            }else{
                sb_no.append("<h3>入职"+noContractDay+"天以上未签订合同的员工共[").append(contractEndList.size()).append("]位。").append("</h3>\r\n");
            }

            String subject = "未签劳动合同/劳动合同到期提醒";
            mailService.sendMimeMail(toPersons,null,subject,sb_end.append(sb_no).toString(),null);

        }catch (Exception e) {

            log.error(e.getMessage());
            e.printStackTrace();

        } finally {

        }

    }




}
