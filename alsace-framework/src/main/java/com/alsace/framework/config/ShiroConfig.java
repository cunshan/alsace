package com.alsace.framework.config;

import com.alsace.framework.common.shiro.ShiroUserService;
import com.alsace.framework.common.shiro.UserRealm;
import javax.annotation.Resource;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

  @Resource
  private ShiroUserService shiroUserService;

  @Resource
  RedisSessionDAO redisSessionDAO;

  @Resource
  RedisCacheManager redisCacheManager;


  @Bean
  @ConditionalOnMissingBean(name = "userRealm")
  public UserRealm userRealm() {
    UserRealm userRealm = new UserRealm(shiroUserService);
    userRealm.setCredentialsMatcher(credentialsMatcher());
    return userRealm;
  }

  // 配置url过滤器
  @Bean
  @ConditionalOnMissingBean(ShiroFilterChainDefinition.class)
  public ShiroFilterChainDefinition shiroFilterChainDefinition() {
    DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
    chainDefinition.addPathDefinitions(shiroUserService.getPathDefinitionsMap());
    return chainDefinition;
  }

  // 设置用于匹配密码的CredentialsMatcher
  @Bean
  @ConditionalOnMissingBean(HashedCredentialsMatcher.class)
  public HashedCredentialsMatcher credentialsMatcher() {
    HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
    credentialsMatcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);  // 散列算法，这里使用更安全的sha256算法
    credentialsMatcher.setStoredCredentialsHexEncoded(false);  // 数据库存储的密码字段使用HEX还是BASE64方式加密
    credentialsMatcher.setHashIterations(2);  // 散列迭代次数
    return credentialsMatcher;
  }

  // 配置security并设置userRealm，避免xxxx required a bean named 'authorizer' that could not be found.的报错
  @Bean
  @ConditionalOnMissingBean(SessionsSecurityManager.class)
  public SessionsSecurityManager securityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(userRealm());
    securityManager.setCacheManager(redisCacheManager);
    securityManager.setSessionManager(sessionManager());
    return securityManager;
  }

  @Bean
  public SessionManager sessionManager() {
    DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    // inject redisSessionDAO
    sessionManager.setSessionDAO(redisSessionDAO);
    return sessionManager;
  }




}
