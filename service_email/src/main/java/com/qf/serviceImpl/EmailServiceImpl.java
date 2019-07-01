package com.qf.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Email;
import com.qf.entity.User;
import com.qf.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    // 发送邮件
    @Override
    public boolean checkEmail(Email email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
            // 设置发送方
            messageHelper.setFrom("18770020835@163.com");
            // 设置接收方
            messageHelper.setTo(email.getToEmail());
            // 设置标题
            messageHelper.setSubject(email.getTopic());
            // 设置内容
            messageHelper.setText(email.getText(),true);
            // 设置发送时间
            messageHelper.setSentDate(new Date());
            // 发送邮件
            javaMailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 注册验证邮箱
    @Override
    public int userRegister(String toEmail){
        Email email = new Email();
        email.setToEmail(toEmail);
        email.setTopic("用户注册");
        // 生成随机验证码
        int code = (int) ((Math.random()*(9999-1000+1))+1000);
        email.setText("验证码为："+code+"，将于30分钟后失效，请尽快使用");
        boolean flag = checkEmail(email);
        if (flag) {
            return code;
        }
        return 0;
    }

    // 重置密码邮件
    @Override
    public boolean forgetPassword(User user) {
        Email email = new Email();
        email.setToEmail(user.getEmail());
        email.setTopic("重置密码");
        email.setText("请点击<a href='http://localhost:8080/user/toResetPassword/"+user.getUsername()+"'>" +
                "http://localhost:8080/user/toResetPassword</a>"+"重置密码，或右键链接打开新标签");
        return checkEmail(email);
    }
}
