package com.uyibai.requestid.dubbo;

import com.uyibai.requestid.RequestIdConstant;
import com.uyibai.requestid.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.MDC;

/**
 * 全链路日志上下文 Dubbo Customer Filter
 *
 * @author Huzi.Wang[imhuzi.wh@gmail.com]
 * @version V1.0.0
 * @date 2020-05-05 12:24
 **/
@Activate(group = CommonConstants.CONSUMER, order = -999)
@Slf4j
public class CustomerLogRequestIdFilter implements Filter {

  @Override
  public Result invoke(Invoker<?> invoker, Invocation invocation) {
    // 如果没有请求的requestid，则将生成的放入进去
    RpcContext.getContext()
        .setAttachment(RequestIdConstant.TRACE_ID, StringUtils.getRequestId(MDC.get(RequestIdConstant.TRACE_ID)));
    RpcContext.getContext().setAttachment(RequestIdConstant.REQUEST_TIME, MDC.get(RequestIdConstant.REQUEST_TIME));
    log.info("dubbo customer start invoke:{},{}", invocation.getServiceName(), invocation.getMethodName());
    return invoker.invoke(invocation);
  }
}
