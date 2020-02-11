package com.alsace.framework.common.shiro;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

@Data
@AllArgsConstructor
public class JwtToken implements AuthenticationToken {

  private static final long serialVersionUID = -8879368348052965588L;
  private String token;

  @Override
  public Object getPrincipal() {
    return token;
  }

  @Override
  public Object getCredentials() {
    return token;
  }
}
