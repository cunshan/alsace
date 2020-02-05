package com.alsace.framework.config;

import com.alsace.framework.config.properties.AlsaceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.alsace")
@EnableConfigurationProperties({AlsaceProperties.class})
public class AlsaceFrameworkAutoConfig {


}
