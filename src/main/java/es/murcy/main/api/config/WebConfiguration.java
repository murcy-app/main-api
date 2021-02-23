package es.murcy.main.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Configuration
public class WebConfiguration {

  @Bean(name = "errorDateTimeFormatter")
  public DateTimeFormatter errorDateTimeFormatter() {
    return DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.from(ZoneOffset.UTC));
  }
}
