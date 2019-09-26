package com.alsace.framework.aspect;


import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

public class LogOperateIdHolder {

  private static ThreadLocal<String> threadLocal = new ThreadLocal<>();


  public static String getId() {
    String id = threadLocal.get();
    if (StringUtils.isNotBlank(id)) {
      return id;
    }
    id = UUID.randomUUID().toString();
    threadLocal.set(id);
    return id;
  }

}
