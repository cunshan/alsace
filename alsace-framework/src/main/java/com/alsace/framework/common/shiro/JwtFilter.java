package com.alsace.framework.common.shiro;

import com.alsace.framework.utils.LogUtils;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {


  @Override
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
      Object mappedValue) {
    try {
      executeLogin(request, response);
    } catch (Exception e) {
      LogUtils.printError(log, e);
      return false;
    }

    return true;
  }


  /**
   * 跨域支持
   */
  @Override
  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    httpServletResponse
        .setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
    httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
    httpServletResponse.setHeader("Access-Control-Allow-Headers",
        httpServletRequest.getHeader("Access-Control-Request-Headers"));
    // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
    if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
      httpServletResponse.setStatus(HttpStatus.OK.value());
      return false;
    }
    return super.preHandle(request, response);
  }

  @Override
  protected boolean executeLogin(ServletRequest request, ServletResponse response)
      throws Exception {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    //获取头部token
    String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
    String token = header.substring(header.indexOf(' ')+1);
    JwtToken jwtToken = new JwtToken(token);
    //登录操作
    getSubject(request, response).login(jwtToken);
    return true;
  }
}