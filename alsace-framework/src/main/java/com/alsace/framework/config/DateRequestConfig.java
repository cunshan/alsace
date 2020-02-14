package com.alsace.framework.config;

import com.alsace.framework.config.properties.AlsaceProperties;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

/**
 * 对于请求参数中的时间类型进行处理
 */
@Configuration
@Data
public class DateRequestConfig {

  @Autowired
  private AlsaceProperties alsaceProperties;

  /**
   * LocalDate转换器，用于转换RequestParam和PathVariable参数
   */
  @Bean
  @ConditionalOnMissingBean
  public Converter<String, LocalDate> localDateConverter() {
    return source -> LocalDate.parse(source, DateTimeFormatter.ofPattern(alsaceProperties.getDateFormat()));
  }

  /**
   * LocalDateTime转换器，用于转换RequestParam和PathVariable参数
   */
  @Bean
  @ConditionalOnMissingBean
  public Converter<String, LocalDateTime> localDateTimeConverter() {
    return source -> LocalDateTime.parse(source, DateTimeFormatter.ofPattern(alsaceProperties.getTimeFormat()));
  }

  /**
   * LocalTime转换器，用于转换RequestParam和PathVariable参数
   */
  @Bean
  @ConditionalOnMissingBean
  public Converter<String, LocalTime> localTimeConverter() {
    return source -> LocalTime.parse(source, DateTimeFormatter.ofPattern(alsaceProperties.getTimeFormat()));
  }

  /**
   * Date转换器，用于转换RequestParam和PathVariable参数
   */
  @Bean
  @ConditionalOnMissingBean
  public Converter<String, Date> dateConverter() {
    return source -> {
      SimpleDateFormat format = new SimpleDateFormat(alsaceProperties.getTimeFormat());
      try {
        return format.parse(source);
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    };
  }


}
