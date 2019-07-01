package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.User;
import com.qf.service.IEmailService;
import com.qf.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class RegisterController {

    @Reference
    private IEmailService emailService;

    @Reference
    private IUserService userService;

    // 从首页跳转到注册页面
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    // 发送注册验证码
    @ResponseBody
    @RequestMapping("/toEmail")
    public boolean toEmail(String toEmail, HttpSession session){
        int code = emailService.userRegister(toEmail);
        // 把验证码存到session中
        session.setAttribute("code",code+"");
        if (code == 0){
            return false;
        }
        return true;
    }

    // 判断验证码是否一致
    @ResponseBody
    @RequestMapping("/checkCode")
    public boolean checkCode(String code,HttpSession session){
        // 从session中获取验证码
        String eCode = (String) session.getAttribute("code");
        // 进行判断
        return code.equals(eCode);
    }

    // 注册
    @RequestMapping("/register")
    public String register(User user){
        boolean flag = userService.insertUser(user);
        if (flag) {
            // 注册成功后跳转到登录页面
            return "redirect:http://localhost:8080/user/toLogin";
        }
        // 注册失败跳转到错误页面
        return "error";
    }

    // 注册时判断用户名是否存在
    @ResponseBody
    @RequestMapping("/getUserByUsername")
    public boolean getUserByUsername(String username){
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return true;
        }
        return false;
    }

}
