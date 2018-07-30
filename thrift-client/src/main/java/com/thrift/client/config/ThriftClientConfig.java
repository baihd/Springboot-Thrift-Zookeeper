package com.thrift.client.config;

import com.thrift.common.thrift.ThriftServiceClientProxyFactory;
import com.thrift.common.zookeeper.ZookeeperFactory;
import com.thrift.common.zookeeper.impl.ThriftServerAddressProviderZookeeper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThriftClientConfig {

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
    public ThriftServerAddressProviderZookeeper thriftServerAddressProviderZookeeper() {
        ThriftServerAddressProviderZookeeper tsapz = new ThriftServerAddressProviderZookeeper();
        tsapz.setService("com.thrift.api.UserService");
        tsapz.setVersion("1.0.0");
        try {
            tsapz.setZkClient(zookeeperFactory().getObject());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tsapz;
    }

    @Bean
    public ThriftServiceClientProxyFactory thriftServiceClientProxyFactory() {
        ThriftServiceClientProxyFactory tscpf = new ThriftServiceClientProxyFactory();
        tscpf.setMaxActive(5);
        tscpf.setIdleTime(1800000);
        tscpf.setServerAddressProvider(thriftServerAddressProviderZookeeper());
        return tscpf;
    }


}
