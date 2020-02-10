package com.alsace.framework.common.shiro;

import java.util.List;
import java.util.Map;

/**
 * shiro框架调用的获取权限的服务接口
 */
public interface ShiroUserService {

  /**
   * 获取初始化shiro的路径跟权限配置列表
   */
  Map<String, String> getPathDefinitionsMap();

  /**
   * 根据账号获取登录用户对应的权限
   */
  List<String> getPermissionList(String loginAccount);

  /**
   * 登录逻辑，返回的是要保存到session中的登录信息
   * @param loginAccount 登录账号
   * @param password 密码
   * @return
   */
  ShiroPrincipal login(String loginAccount, char[] password);
}
