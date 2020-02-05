package com.alsace.framework.config;

import com.alsace.framework.common.log.LogService;
import com.alsace.framework.common.log.SimpleLogService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {


  /**
   * 默认日志持久化服务
   * 只负责打印日志记录，不保存任何数据库，如果需要持久化到数据库中，需要实现
   * @see com.alsace.framework.common.log.LogService 在其中持久化日志
   */
  @Bean
  @ConditionalOnMissingBean(LogService.class)
  public LogService logService(){
    return new SimpleLogService();
  }




}
