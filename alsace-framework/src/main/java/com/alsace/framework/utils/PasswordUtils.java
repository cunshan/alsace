package com.alsace.framework.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class PasswordUtils {

  /**
   * 简单MD5加密
   */
  public static String md5(String source){
    return new Md5Hash(source).toBase64();
  }

  /**
   * 简单MD5加密
   */
  public static String md5(String source,String salt){
    return new Md5Hash(source,salt).toBase64();
  }


  /**
   * 简单MD5加密
   */
  public static String md5(String source,String salt,int hashIterations){
    return new Md5Hash(source,salt,hashIterations).toBase64();
  }

}
