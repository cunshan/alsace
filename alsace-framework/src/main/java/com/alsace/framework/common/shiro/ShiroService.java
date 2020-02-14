package com.alsace.framework.common.shiro;

import java.util.List;
import java.util.Map;
import org.apache.shiro.authc.AuthenticationException;

/**
 * shiro框架调用的获取权限的服务接口
 */
public interface ShiroService {

  /**
   * 获取初始化shiro的路径跟权限配置列表
   */
  Map<String, String> getPathDefinitionsMap();

  /**
   * 根据账号获取登录用户对应的权限
   */
  List<String> getPermissionList(String loginAccount);

  /**
   * 普通登录逻辑，返回的是要保存到session中的登录信息
   */
  ShiroPrincipal login(String loginAccount, String passwordOrToken) throws AuthenticationException;

}
