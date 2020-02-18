package com.alsace.framework.common.shiro;

import com.alsace.framework.common.basic.AlsaceResponse.Builder;
import com.alsace.framework.utils.GsonUtils;
import com.alsace.framework.utils.LogUtils;
import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;

@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {


  private static final String BEARER = "Bearer";

  public JwtFilter() {
    setAuthcScheme(BEARER);
    setAuthzScheme(BEARER);
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
//    // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
//    if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//      httpServletResponse.setStatus(HttpStatus.OK.value());
//      return false;
//    }
    //设置返回头信息
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json;charset=UTF-8");
    return super.preHandle(request, response);
  }

  /**
   * 获取头部token
   */
  private JwtToken getJwtToken(ServletRequest request) {
    //获取头部token
    String header = this.getAuthzHeader(request);
    String token = header.substring(header.indexOf(' ') + 1);
    return new JwtToken(token);
  }


  @Override
  protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
    return getJwtToken(request);
  }


  @Override
  protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ex,
      ServletRequest request, ServletResponse response) {
    try {
      response.getWriter().println(GsonUtils.toJson(
          new Builder(false).code(HttpStatus.UNAUTHORIZED.value()).msg(ex.getMessage()).build()));
    } catch (IOException e) {
      LogUtils.error(log, e);
    }
    return false;
  }

  @Override
  protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
    try {
      response.getWriter().println(GsonUtils.toJson(
          new Builder(false).code(HttpStatus.UNAUTHORIZED.value()).msg("Token无效，请重新登录！").build()));
    } catch (IOException e) {
      LogUtils.error(log, e);
    }
    return false;
  }
}
