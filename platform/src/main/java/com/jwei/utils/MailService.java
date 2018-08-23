package com.jwei.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created by wyb on 2018/7/30.
 */
@Service
public class MailService {

    @Resource
    private JavaMailSender mailSender;
    @Resource
    private SimpleMailMessage simpleMail;

    public void sendSimpleMail(String[] to,String[] cc,String subject,String content) throws Exception{
        long startTime=System.currentTimeMillis();
        if(to==null || StringUtils.isAnyBlank(subject,content)){
            throw new ExceptionAPI("关键参数错误[收件人/邮件主题/邮件内容]");
        }
        simpleMail.setTo(to);  //收件人
        if(cc!=null){
            simpleMail.setCc(cc);  //抄送
        }
        //mailMessage.setBcc();   //密送 暂不用
        simpleMail.setSubject(subject);
        simpleMail.setText(content);
        mailSender.send(simpleMail);
        long endTime=System.currentTimeMillis();
        System.out.println("发送SimpleMail时间："+(endTime-startTime));
    }

    //MimeMessage  可以使用附件、邮件模块等
    public void sendMimeMail(String[] to,String[] cc,String subject,String content,String attachmentPath) throws Exception {
        long startTime=System.currentTimeMillis();
        if(to==null || StringUtils.isAnyBlank(subject,content)){
            throw new ExceptionAPI("关键参数错误[收件人/邮件主题/邮件内容]");
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");

        mimeHelper.setFrom("759716722@qq.com");
        mimeHelper.setTo(to);
        if(cc!=null){
            mimeHelper.setCc(cc);
        }
        mimeHelper.setSubject(subject);
        mimeHelper.setText(content,true); // true 表示启动HTML格式的邮件
        if(StringUtils.isNotBlank(attachmentPath)){
            FileSystemResource file = new FileSystemResource(new File(attachmentPath));
            mimeHelper.addAttachment("附件",file); //添加附件
        }
        mailSender.send(mimeMessage);
        long endTime=System.currentTimeMillis();
        System.out.println("发送MimeMail时间："+(endTime-startTime));
    }


}
