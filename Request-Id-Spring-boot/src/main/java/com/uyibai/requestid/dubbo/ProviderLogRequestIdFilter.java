package com.uyibai.requestid.dubbo;

import com.uyibai.requestid.RequestIdConstant;

import lombok.extern.slf4j.Slf4j;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.slf4j.MDC;

/**
 * 全链路日志上下文 Dubbo Provider Filter
 *
 * @author Huzi.Wang[imhuzi.wh@gmail.com]
 * @version V1.0.0
 * @date 2020-05-05 12:24
 **/
@Activate(group = CommonConstants.PROVIDER, order = -1000)
@Slf4j
public class ProviderLogRequestIdFilter implements Filter {

  @Override
  public Result invoke(Invoker<?> invoker, Invocation invocation) {
    MDC.put(RequestIdConstant.TRACE_ID, invocation.getAttachment(RequestIdConstant.TRACE_ID));
    MDC.put(RequestIdConstant.REQUEST_TIME, invocation.getAttachment(RequestIdConstant.REQUEST_TIME));
    log.info("dubbo provider start invoke:{},{}", invocation.getServiceName(), invocation.getMethodName());
    try {
      return invoker.invoke(invocation);
    } finally {
      log.info("dubbo provider end invoke:{},{}", invocation.getServiceName(), invocation.getMethodName());
      MDC.remove(RequestIdConstant.TRACE_ID);
      MDC.remove(RequestIdConstant.REQUEST_TIME);
    }
  }
}
