package com.alsace.framework.config;

import com.alsace.framework.common.exception.BizException;
import com.alsace.framework.config.properties.AlsaceProperties;
import com.alsace.framework.utils.LogUtils;
import java.io.IOException;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.anumbrella.seaweedfs.core.FileSource;
import net.anumbrella.seaweedfs.core.FileTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty("alsace.seaweedfs.enabled")
@Slf4j
public class SeaweedfsConfig {

  @Resource
  private AlsaceProperties alsaceProperties;

  @Bean
  @ConditionalOnMissingBean
  public FileTemplate fileTemplate(){

    FileSource fileSource = new FileSource();
    // SeaweedFS master server host
    fileSource.setHost(alsaceProperties.getSeaweedfs().getHost());
    // SeaweedFS master server port
    fileSource.setPort(alsaceProperties.getSeaweedfs().getPort());
    // Set Connection Timeout
    fileSource.setConnectionTimeout(alsaceProperties.getSeaweedfs().getTimeout());
    // Startup manager and listens for the change
    try {
      fileSource.startup();
    } catch (IOException ex) {
      LogUtils.error(log,ex);
      throw new BizException(ex);
    }
    LogUtils.info(log,"FileTemplate 配置完成");
    return new FileTemplate(fileSource.getConnection());
  }

}
