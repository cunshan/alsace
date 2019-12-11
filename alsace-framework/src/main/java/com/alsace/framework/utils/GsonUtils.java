package com.alsace.framework.utils;

import com.google.gson.Gson;

/**
 * Gson工具类，主要包含序列化对象工具
 */
public class GsonUtils {

  private static final Gson gson = new Gson();

  /**
   * 获取对象的json字符串
   */
  public static String toJson(Object obj){
    return gson.toJson(obj);
  }

  /**
   * 解析json字符串
   */
  public static  <T> T fromJson(String jsonStr,Class<T> clazz){
    return gson.fromJson(jsonStr,clazz);
  }

}
