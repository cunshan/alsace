package com.alsace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.alsace.service")
public class AlsaceServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(AlsaceServiceApplication.class);
  }

}
