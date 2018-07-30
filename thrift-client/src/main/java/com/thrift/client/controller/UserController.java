package com.thrift.client.controller;

import com.thrift.api.UserService;
import com.thrift.client.utils.ApplicationContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @ResponseBody
    @RequestMapping(value = "/hello")
    public String hello() {
        try {
            UserService.Iface userBussiness = (UserService.Iface) ApplicationContextUtil.getBean("thriftServiceClientProxyFactory");
            String str = userBussiness.sayHello("hihao");
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
