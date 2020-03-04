package com.alsace.framework.config.properties;

import lombok.Data;

@Data
public class SeaweedfsProperties {

  private boolean enabled = false;
  private String host = "localhost";
  private int port = 9333;
  private int timeout = 5000;

}
