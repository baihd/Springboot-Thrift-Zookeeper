package com.thrift.server.config;

import com.thrift.common.thrift.ThriftServiceServerFactory;
import com.thrift.common.zookeeper.ZookeeperFactory;
import com.thrift.common.zookeeper.impl.ThriftServerAddressRegisterZookeeper;
import com.thrift.server.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThriftServerConfig {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Bean
    public ZookeeperFactory zookeeperFactory() {
        ZookeeperFactory zookeeperFactory = new ZookeeperFactory();
        zookeeperFactory.setZkHosts("127.0.0.1:2181");
        zookeeperFactory.setNamespace("com.thrift.api");
        zookeeperFactory.setConnectionTimeout(3000);
        zookeeperFactory.setSessionTimeout(3000);
        zookeeperFactory.setSingleton(true);
        return zookeeperFactory;
    }

    @Bean
    public ThriftServerAddressRegisterZookeeper thriftServerAddressRegisterZookeeper() {
        ThriftServerAddressRegisterZookeeper tsarz = new ThriftServerAddressRegisterZookeeper();
        try {
            tsarz.setZkClient(zookeeperFactory().getObject());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tsarz;
    }

    @Bean
    public ThriftServiceServerFactory thriftServiceServerFactory() {
        ThriftServiceServerFactory tssf = new ThriftServiceServerFactory();
        tssf.setService(userServiceImpl);
        tssf.setPort(9002);
        tssf.setVersion("1.0.0");
        tssf.setWeight(1);
        tssf.setThriftServerAddressRegister(thriftServerAddressRegisterZookeeper());
        return tssf;
    }
}
