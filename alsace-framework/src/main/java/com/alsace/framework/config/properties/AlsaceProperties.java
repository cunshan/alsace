package com.alsace.framework.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alsace")
@Component
@Data
public class AlsaceProperties {


  private String dateFormat = "yyyy-MM-dd";
  private String timeFormat = "yyyy-MM-dd HH:mm:ss";



}
