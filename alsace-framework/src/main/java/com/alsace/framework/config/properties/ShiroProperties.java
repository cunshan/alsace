package com.alsace.framework.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "shiro")
@Component
@Data
public class ShiroProperties {

  private boolean jwt = false;//是否是jwt验证
  private boolean enabled = true;//是否开启shiro

  private SessionManageProperties sessionManager;
  private ShiroWebProperties web;

  private String unauthorizedUrl;
  private String loginUrl;
  private String successUrl;


  @Data
  class SessionManageProperties{
    private boolean sessionIdCookieEnabled;
    private boolean sessionIdUrlRewritingEnabled;
  }

  @Data
  class ShiroWebProperties {
    private boolean enabled;
  }
}
