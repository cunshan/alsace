package com.alsace.framework.common.basic;

import java.io.Serializable;

/**
 * 请求返回参数
 */
public class AlsaceResponse implements Serializable {

  private static final long serialVersionUID = 8933245719351162749L;

  private int code;//返回编码

  private boolean success;//请求是否成功

  private String msg;//返回的信息

  private Object data;//返回的业务数据


  private AlsaceResponse(Builder builder){
    this.code = builder.code;
    this.success = builder.success;
    this.msg = builder.msg;
    this.data = builder.data;
  }


  public static class Builder{
    private int code;//返回状态编码

    private boolean success;//请求是否成功

    private String msg;//返回的信息

    private Object data;//返回的业务数据


    public void Builder(boolean success){
      this.success = success;
    }

    public Builder code(int code){
      this.code = code;
      return this;
    }

    public Builder msg(String msg){
      this.msg = msg;
      return this;
    }

    public Builder data(Object data){
      this.data = data;
      return this;
    }

    public AlsaceResponse build(){
      return new AlsaceResponse(this);
    }

  }

}
