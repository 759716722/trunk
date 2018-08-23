package com.jwei.api;

import com.jwei.task.HrTaskMain;
import com.jwei.utils.JVMInfo;
import com.jwei.utils.MailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyb on 2018/7/30.
 */

@RestController
@RequestMapping("/api/mail")
public class MailTestAction {
    @Resource
    private MailService mailService;

    @RequestMapping("/sendMail.do")
    public Map<String,Object> getParam(){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            String[] toPersons = {"759716722@qq.com"};
            String subject = "测试邮件";
            String content = "<div>测试邮件内容</div>";
            mailService.sendSimpleMail(toPersons,null,subject,content);
            resultMap.put("flag","Y");
            resultMap.put("data","成功");

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/getJVMInfo.do")
    public Map<String,Object> getJVMInfo(){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            JVMInfo jvmInfo = new JVMInfo();
            Map dataMap = jvmInfo.getJVMInfo();
            resultMap.put("flag","Y");
            resultMap.put("data",dataMap);

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/doSendHrEmail.do")
    public Map<String,Object> doSendHrEmail(){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            HrTaskMain jvmInfo = new HrTaskMain();
            jvmInfo.doBirthdayNotify();
            jvmInfo.doContractNotify();
            resultMap.put("flag","Y");
            resultMap.put("data","success");

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }



}
