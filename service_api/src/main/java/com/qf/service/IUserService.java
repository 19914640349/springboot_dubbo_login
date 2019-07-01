package com.qf.service;

import com.qf.entity.User;

public interface IUserService {

    // 添加用户
    boolean insertUser(User user);

    // 注册：根据用户名和密码查询
    boolean getUserByLogin(String username,String password);

    // 忘记密码功能
    String forgetPassword(String username);

    // 根据用户名查询
    User getUserByUsername(String username);

    boolean resetPassword(User user);

}
