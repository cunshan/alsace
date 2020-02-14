package com.alsace.framework.config;

import com.alsace.framework.config.properties.AlsaceProperties;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.annotation.Resource;
import lombok.Data;
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

  @Resource
  private AlsaceProperties alsaceProperties;

  /**
   * LocalDate转换器，用于转换RequestParam和PathVariable参数
   */
  @Bean
  @ConditionalOnMissingBean
  public Converter<String, LocalDate> localDateConverter() {
    return new Converter<String, LocalDate>() {
      @Override
      public LocalDate convert(String source) {
        return LocalDate
            .parse(source, DateTimeFormatter.ofPattern(alsaceProperties.getDateFormat()));
      }
    };
  }

  /**
   * LocalDateTime转换器，用于转换RequestParam和PathVariable参数
   */
  @Bean
  @ConditionalOnMissingBean
  public Converter<String, LocalDateTime> localDateTimeConverter() {
    return new Converter<String, LocalDateTime>() {
      @Override
      public LocalDateTime convert(String source) {
        return LocalDateTime
            .parse(source, DateTimeFormatter.ofPattern(alsaceProperties.getTimeFormat()));
      }
    };
  }

  /**
   * LocalTime转换器，用于转换RequestParam和PathVariable参数
   */
  @Bean
  @ConditionalOnMissingBean
  public Converter<String, LocalTime> localTimeConverter() {
    return new Converter<String, LocalTime>() {
      @Override
      public LocalTime convert(String source) {
        return LocalTime
            .parse(source, DateTimeFormatter.ofPattern(alsaceProperties.getTimeFormat()));
      }
    };
  }

  /**
   * Date转换器，用于转换RequestParam和PathVariable参数
   */
  @Bean
  @ConditionalOnMissingBean
  public Converter<String, Date> dateConverter() {
    return new Converter<String, Date>() {
      @Override
      public Date convert(String source) {
        SimpleDateFormat format = new SimpleDateFormat(alsaceProperties.getTimeFormat());
        try {
          return format.parse(source);
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }
      }
    };
  }


}
