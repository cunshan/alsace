package com.alsace.framework.common.shiro;

import com.alsace.framework.utils.JwtUtils;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

@Data
@EqualsAndHashCode(callSuper = false)
public class JwtRealm extends AuthorizingRealm {

  private ShiroUserService shiroUserService;

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    String token = principals.getPrimaryPrincipal().toString();
    String loginAccount = JwtUtils.getLoginAccount(token);
    //获取权限列表
    List<String> perms = shiroUserService.getPermissionList(loginAccount);
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    authorizationInfo.addRoles(perms);
    return authorizationInfo;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    JwtToken jwtToken = (JwtToken) authenticationToken;
    String token = jwtToken.getToken();
    String loginAccount = JwtUtils.getLoginAccount(token);
    if (StringUtils.isBlank(loginAccount)) {
      throw new AuthenticationException("无效Token！");
    }
    ShiroPrincipal principal = shiroUserService.findPrincipalByLoginAccount(loginAccount);
    if (principal == null) {
      throw new AuthenticationException("登录账号不存在！");
    }
    if (!JwtUtils.verify(token, loginAccount, principal.getPassword())) {
      throw new AuthenticationException("Token失效，请重新登录！");
    }

    return new SimpleAuthenticationInfo(token, token, getName());
  }

  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof JwtToken;
  }
}
