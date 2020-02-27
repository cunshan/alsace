package com.alsace.framework.common.enums;

public enum ActiveFlag {
  YES(1),NO(0);

  private int value;

  ActiveFlag(int value) {
    this.value = value;
  }

  public  int value(){
    return this.value;
  }

}
