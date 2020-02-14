package com.alsace.framework.config;

import com.alsace.framework.common.shiro.JwtFilter;
import com.alsace.framework.common.shiro.JwtRealm;
import com.alsace.framework.common.shiro.ShiroService;
import com.alsace.framework.common.shiro.UserRealm;
import com.alsace.framework.config.properties.AlsaceProperties;
import com.alsace.framework.config.properties.ShiroProperties;
import com.alsace.framework.utils.LogUtils;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@ConditionalOnProperty(name = {"shiro.enabled","shiro.web.enabled"})
public class ShiroConfig {

  @Resource
  private ShiroService shiroService;

  @Resource
  private RedisSessionDAO redisSessionDAO;

  @Resource
  private RedisCacheManager redisCacheManager;

  @Resource
  private ShiroProperties shiroProperties;

  @Bean
  @ConditionalOnMissingBean(name = "userRealm")
  public AuthorizingRealm userRealm() {
    if (shiroProperties.isJwt()) {
      JwtRealm jwtRealm = new JwtRealm();
      jwtRealm.setShiroService(shiroService);
      jwtRealm.setAuthorizationCachingEnabled(true);
      jwtRealm.setAuthenticationCachingEnabled(true);
      jwtRealm.setCachingEnabled(true);
      LogUtils.info(log, "完成配置userRealm-jwt");

      return jwtRealm;
    }
    UserRealm userRealm = new UserRealm(shiroService);
    userRealm.setCredentialsMatcher(credentialsMatcher());
    userRealm.setAuthorizationCachingEnabled(true);
    userRealm.setAuthenticationCachingEnabled(true);
    userRealm.setCachingEnabled(true);
    LogUtils.info(log, "完成配置userRealm");
    return userRealm;
  }

  /**
   * 设置用于匹配密码的CredentialsMatcher
   */
  private HashedCredentialsMatcher credentialsMatcher() {
    HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
    credentialsMatcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);  // 散列算法，这里使用更安全的sha256算法
    credentialsMatcher.setStoredCredentialsHexEncoded(false);  // 数据库存储的密码字段使用HEX还是BASE64方式加密
    credentialsMatcher.setHashIterations(2);  // 散列迭代次数
    LogUtils.info(log, "完成配置credentialsMatcher");
    return credentialsMatcher;
  }


  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SessionsSecurityManager securityManager,
      ShiroFilterChainDefinition shiroFilterChainDefinition) {
    ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

    filterFactoryBean.setLoginUrl(shiroProperties.getLoginUrl());
    filterFactoryBean.setSuccessUrl(shiroProperties.getSuccessUrl());
    filterFactoryBean.setUnauthorizedUrl(shiroProperties.getUnauthorizedUrl());

    filterFactoryBean.setSecurityManager(securityManager);
    filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());

    //增加自定义filter
    Map<String, Filter> filterMap = new LinkedHashMap<>();
    filterMap.put("jwt", new JwtFilter());
    filterFactoryBean.setFilters(filterMap);
    return filterFactoryBean;
  }

  /**
   * url过滤配置
   */
  @Bean
  @ConditionalOnMissingBean(ShiroFilterChainDefinition.class)
  public ShiroFilterChainDefinition shiroFilterChainDefinition() {
    DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
    chainDefinition.addPathDefinitions(shiroService.getPathDefinitionsMap());
    return chainDefinition;
  }

  /**
   * 配置security并设置userRealm，避免xxxx required a bean named 'authorizer' that could not be found.的报错
   */
  @Bean
  @ConditionalOnMissingBean(SessionsSecurityManager.class)
  public SessionsSecurityManager securityManager(AuthorizingRealm userRealm) {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(userRealm);
    securityManager.setCacheManager(redisCacheManager);
    if (!shiroProperties.isJwt()) {
      securityManager.setSessionManager(sessionManager());
    } else {
      /*
       * 关闭shiro自带的session，详情见文档
       * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
       */
      DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
      DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
      sessionStorageEvaluator.setSessionStorageEnabled(false);
      subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
      securityManager.setSubjectDAO(subjectDAO);
    }

    LogUtils.info(log, "完成配置securityManager");
    return securityManager;
  }


  /**
   * session管理配置
   */
  private SessionManager sessionManager() {
    DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    // inject redisSessionDAO
    sessionManager.setSessionDAO(redisSessionDAO);
    LogUtils.info(log, "完成配置sessionManager");
    return sessionManager;
  }


}
