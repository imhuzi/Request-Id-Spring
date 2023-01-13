package com.examples;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  @GetMapping(value = {"/index", ""})
  public Object index() {
    Map k = new HashMap();
    k.put("time", LocalDateTime.now());
    return k;
  }
}
