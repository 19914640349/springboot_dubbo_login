package com.qf.serviceImpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IEmailService;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Reference
    private IEmailService emailService;

    // 添加用户
    @Override
    public boolean insertUser(User user) {
        int result = userMapper.insert(user);
        if (result > 0) {
            return true;
        }
        return false;
    }

    // 根据用户名和密码获取用户
    @Override
    public boolean getUserByLogin(String username, String password) {
        User user = userMapper.getUserByLogin(username, password);
        if (user != null) {
            return true;
        }
        return false;
    }

    // 通过用户名获取用户
    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    // 忘记密码
    @Override
    public String forgetPassword(String username) {
        // 先查询用户是否存在
        User user = getUserByUsername(username);
        if (user != null) {
            // 发送邮件 成功返回true 失败返回false
            boolean flag = emailService.forgetPassword(user);
            if (flag) {
                return "true";
            }
            return "false";
        }
        return null;
    }

    // 重置密码
    @Override
    public boolean resetPassword(User user) {
        // 获取用户信息
        User oldUser = getUserByUsername(user.getUsername());
        // 更换密码
        oldUser.setPassword(user.getPassword());
        // 更新用户信息
        int result = userMapper.updateById(oldUser);
        if (result > 0) {
            return true;
        }
        return false;
    }
}
