package com.alsace.framework.common.enums;

public enum  LogModifyType {

  CREATE,UPDATE,DELETE,QUERY;


  @Override
  public String toString() {
    return this.name();
  }
}
