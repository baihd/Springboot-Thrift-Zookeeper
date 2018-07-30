package com.thrift.common.thrift;

import org.apache.thrift.TServiceClient;

public interface PoolOperationCallBack {
    // 创建成功是执行
    void make(TServiceClient client);

    // 销毁client之前执行
    void destroy(TServiceClient client);
}
