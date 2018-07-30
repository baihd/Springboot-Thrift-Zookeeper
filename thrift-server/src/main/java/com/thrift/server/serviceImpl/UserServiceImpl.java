package com.thrift.server.serviceImpl;

import com.thrift.api.UserService;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService.Iface {
    public String sayHello(String username) throws TException {
        return "hello" + username;
    }

    public String getRandom() throws TException {
        return "random";
    }
}
