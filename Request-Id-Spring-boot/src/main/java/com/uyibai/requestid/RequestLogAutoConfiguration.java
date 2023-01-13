package com.uyibai.requestid;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author : Hui.Wang [huzi.wh@gmail.com]
 * @version : 1.0
 * @date  : 2023/1/13
 */
@Configuration
public class RequestLogAutoConfiguration {

  @Bean
  @ConditionalOnClass(name = "org.springframework.web.bind.annotation.RequestMapping")
  public ApiRequestLogAspect apiRequestLogAspect() {
    return new ApiRequestLogAspect();
  }
}
