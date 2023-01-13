package com.uyibai.requestid.utils;

import java.util.UUID;

import org.springframework.lang.Nullable;

/**
 * 自定义 String utils
 *
 * @author : Hui.Wang [huzi.wh@gmail.com]
 * @version : 1.0
 * @date  : 2023/1/12
 */
public class StringUtils {

  public static String defaultValue(String value, String def) {
    if (isEmpty(value) || !hasText(value)) {
      return def;
    }
    return value;
  }

  public static String getRequestId(String value) {
    if (isEmpty(value) || !hasText(value)) {
      return UUID.randomUUID().toString().replaceAll("-","");
    }
    return value;
  }


  public static boolean isEmpty(@Nullable Object str) {
    return str == null || "".equals(str);
  }


  public static boolean hasText(@Nullable CharSequence str) {
    return str != null && str.length() > 0 && containsText(str);
  }

  public static boolean containsText(CharSequence str) {
    int strLen = str.length();

    for (int i = 0; i < strLen; ++i) {
      if (!Character.isWhitespace(str.charAt(i))) {
        return true;
      }
    }

    return false;
  }
}
