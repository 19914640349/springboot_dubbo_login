package com.qf.service;

import com.qf.entity.Email;
import com.qf.entity.User;

public interface IEmailService {

    boolean checkEmail(Email email);

    int userRegister(String toEmail);

    boolean forgetPassword(User user);

}
