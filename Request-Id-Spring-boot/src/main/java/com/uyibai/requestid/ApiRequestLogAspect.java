package com.uyibai.requestid;


import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

/**
 * 接口 请求和响应参数 打印
 */
@Aspect
@Order(-1)
@Slf4j
public class ApiRequestLogAspect {

  @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)" +
      "||@annotation(org.springframework.web.bind.annotation.GetMapping)" +
      "||@annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
      "||@annotation(org.springframework.web.bind.annotation.PutMapping)" +
      "||@annotation(org.springframework.web.bind.annotation.PatchMapping)" +
      "||@annotation(org.springframework.web.bind.annotation.PostMapping)")
  public void controllerMethodPointcut() {
  }

  @Around("controllerMethodPointcut()")
  public Object aroundController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    Object result = null;
    HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(
        RequestContextHolder.getRequestAttributes())).getRequest();

    String xRequestId = com.uyibai.requestid.utils.StringUtils.getRequestId(
        request.getHeader(RequestIdConstant.NGINX_INGRESS_REQUEST_ID));
    String path = request.getServletPath();
    String method = request.getMethod();
    long startTime = System.currentTimeMillis();
    MDC.put(RequestIdConstant.TRACE_ID, xRequestId);
    MDC.put(RequestIdConstant.REQUEST_TIME, String.valueOf(startTime));
    // 将 x-request-id 放入 mdc
    log.info("{}->{},params:{}", method, path, proceedingJoinPoint.getArgs());
    try {
      result = proceedingJoinPoint.proceed();
    } catch (Throwable e) {
      log.error("{}->{},error info:", method, path, e);
      throw e;
    } finally {
      long endTime = System.currentTimeMillis();
      log.info("{}->{},elapsed time(ms):{}", method, path, endTime - startTime);
      // 清除MDC中的值
      MDC.remove(RequestIdConstant.TRACE_ID);
      MDC.remove(RequestIdConstant.REQUEST_TIME);
    }

    //后置通知
    return result;
  }
}
