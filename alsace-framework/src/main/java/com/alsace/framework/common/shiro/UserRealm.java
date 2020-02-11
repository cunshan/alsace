package com.alsace.framework.common.shiro;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;


@Data
@AllArgsConstructor
public class UserRealm extends AuthorizingRealm {

  private ShiroUserService shiroUserService;

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    ShiroPrincipal principal = (ShiroPrincipal) principalCollection.getPrimaryPrincipal();
    String loginAccount = principal.getLoginAccount();
    //获取权限列表
    List<String> perms = shiroUserService.getPermissionList(loginAccount);
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    authorizationInfo.addRoles(perms);
    return authorizationInfo;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    // 登录校验
    UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
    //用户登录逻辑
    ShiroPrincipal principal = shiroUserService
        .login(token.getUsername(), new String(token.getPassword()));
    return new SimpleAuthenticationInfo(principal, principal.getPassword(),
        ByteSource.Util.bytes("user"), getName());
  }


  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof UsernamePasswordToken;
  }
}
