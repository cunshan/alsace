package com.alsace.framework.common.basic;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * controller基类
 */
public class BaseController {

  @InitBinder
  protected void initBinder(WebDataBinder webDataBinder) {
    //页面来的字符类型,去除前后空格
    webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
  }

}
