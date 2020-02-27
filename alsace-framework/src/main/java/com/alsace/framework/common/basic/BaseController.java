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

  protected AlsaceResponse buildResponse(Object data){
    return new AlsaceResponse.Builder().data(data).build();
  }

  protected AlsaceResponse buildResponse(String msg){
    return new AlsaceResponse.Builder().msg(msg).build();
  }

  protected AlsaceResponse buildResponse(String msg,Object data){
    return new AlsaceResponse.Builder().msg(msg).data(data).build();
  }

  protected AlsaceResponse buildResponse(){
    return new AlsaceResponse.Builder().build();
  }

  protected AlsaceResponse buildErrorRespone(String message){
    return new AlsaceResponse.Builder(false).msg(message).build();
  }

  protected AlsaceResponse buildErrorRespone(String message,Object data){
    return new AlsaceResponse.Builder(false).msg(message).data(data).build();
  }

  protected AlsaceResponse buildErrorRespone(String message,Object data,int code){
    return new AlsaceResponse.Builder(false).code(code).msg(message).data(data).build();
  }

  protected AlsaceResponse buildErrorRespone(String message,int code){
    return new AlsaceResponse.Builder(false).code(code).msg(message).build();
  }


}
