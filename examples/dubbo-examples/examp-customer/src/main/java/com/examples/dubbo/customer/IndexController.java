package com.examples.dubbo.customer;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examples.dubbo.ExampService;

/**
 *
 *
 * @author : Hui.Wang [huzi.wh@gmail.com]
 * @version : 1.0
 * @date  : 2023/1/13
 */
@RestController
@RequestMapping(value = "/")
public class IndexController {

  @Reference
  ExampService exampService;

  @GetMapping(value = {"/index", ""})
  public Object index() {
    Map k = new HashMap();
    k.put("time", LocalDateTime.now());
    return k;
  }


  @GetMapping(value = {"/doAction", ""})
  public Object doAction(String action) {
    Map k = new HashMap();
    exampService.doAction(action);
    k.put("time", LocalDateTime.now());
    return k;
  }

  @GetMapping(value = {"/getStringId", ""})
  public Object getStringId(String id) {
    Map k = new HashMap();
    k.put("time", exampService.getStringId(id));
    return k;
  }



}
