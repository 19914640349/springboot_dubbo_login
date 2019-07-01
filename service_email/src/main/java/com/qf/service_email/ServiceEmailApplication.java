package com.qf.service_email;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.qf")
@DubboComponentScan("com.qf.serviceImpl")
public class ServiceEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceEmailApplication.class, args);
    }

}
