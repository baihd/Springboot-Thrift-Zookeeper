package com.thrift.common.zookeeper.impl;

import com.thrift.common.exception.ThriftException;
import com.thrift.common.zookeeper.ThriftServerAddressRegister;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 注册服务列表到Zookeeper
 */
public class ThriftServerAddressRegisterZookeeper implements ThriftServerAddressRegister {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private CuratorFramework zkClient;

    public ThriftServerAddressRegisterZookeeper() {
    }

    public ThriftServerAddressRegisterZookeeper(CuratorFramework zkClient) {
        this.zkClient = zkClient;
    }

    public void setZkClient(CuratorFramework zkClient) {
        this.zkClient = zkClient;
    }

    @Override
    public void register(String service, String version, String address) {
        if (zkClient.getState() == CuratorFrameworkState.LATENT) {
            zkClient.start();
        }
        if (version == null || version == "") {
            version = "1.0.0";
        }
        //创建临时节点
        try {
            zkClient.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath("/" + service + "/" + version + "/" + address);
        } catch (Exception e) {
            logger.error("register api address to zookeeper exception:{}", e);
            throw new ThriftException("register api address to zookeeper exception:{}", e);
        }
    }

    public void close() {
        zkClient.close();
    }
}
