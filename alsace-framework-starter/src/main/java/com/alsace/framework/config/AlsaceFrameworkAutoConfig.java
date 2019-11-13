package com.alsace.framework.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty("com.alsace")
@ComponentScan("com.alsace")
@Configuration
public class AlsaceFrameworkAutoConfig {


}
