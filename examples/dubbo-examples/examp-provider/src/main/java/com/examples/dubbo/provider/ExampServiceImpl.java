package com.examples.dubbo.provider;

import java.util.UUID;

import org.apache.dubbo.config.annotation.Service;

import com.examples.dubbo.ExampService;

import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * @author : Hui.Wang [huzi.wh@gmail.com]
 * @version : 1.0
 * @date  : 2023/1/13
 */
@Service
@Slf4j
public class ExampServiceImpl implements ExampService {

  @Override
  public void doAction(String param) {
    log.info("doAcation:{}", param);
  }

  @Override
  public String getStringId(String param) {
    log.info("getStringId:{}", param);
    return UUID.randomUUID().toString();
  }
}
