package com.thrift.common.thrift.impl;

import com.thrift.common.thrift.PoolOperationCallBack;
import org.apache.thrift.TServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoolOperationCallBackImpl implements PoolOperationCallBack {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void make(TServiceClient client) {
        logger.info("create");
    }

    @Override
    public void destroy(TServiceClient client) {
        logger.info("destroy");
    }

}
