package com.alsace.framework.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty("com.alsace")
@Configuration
@ComponentScan("com.alsace")
public class AlsaceFrameworkAutoConfig {


}
