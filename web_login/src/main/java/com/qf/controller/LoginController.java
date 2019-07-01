package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.User;
import com.qf.service.IEmailService;
import com.qf.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class LoginController {

    @Reference
    private IUserService userService;

    // 进入登录页面
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "index";
    }

    // 登录
    @RequestMapping("/login")
    public String login(String username, String password, Model model){
        boolean flag = userService.getUserByLogin(username, password);
        if (flag){
            return "user";
        }
        model.addAttribute("msg","账号或密码错误");
        return "index";
    }

    // 进入忘记密码页面
    @RequestMapping("/toForgetPassword")
    public String toForgetPassword(){
        return "forgetPassword";
    }

    // 忘记密码发送邮件
    @RequestMapping("/forgetPassword")
    public String forgetPassword(String username,Model model){
        String isOK = userService.forgetPassword(username);
        if ("true".equals(isOK)) {
            return "success";
        }else if ("false".equals(isOK)){
            return "error";
        }
        model.addAttribute("msg","此用户名不存在");
        return "forgetPassword";
    }

    // 进入重置密码页面
    @RequestMapping("/toResetPassword/{username}")
    public String toResetPassword(@PathVariable String username, Model model){
        model.addAttribute("username",username);
        return "resetPassword";
    }

    // 重置密码
    @RequestMapping("/resetPassword")
    public String resetPassword(User user){
        boolean flag = userService.resetPassword(user);
        if (flag){
            return "index";
        }
        return "error";
    }

}
