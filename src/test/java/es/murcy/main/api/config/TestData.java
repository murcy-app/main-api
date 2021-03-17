package es.murcy.main.api.config;

import org.springframework.context.annotation.Bean;

import es.murcy.main.api.config.properties.JsonWebTokenProperties;
import es.murcy.main.api.dto.mapper.ModelMapper;

public class TestData {

  @Bean
  public JsonWebTokenProperties jsonWebTokenProperties() {
    JsonWebTokenProperties jsonWebTokenProperties = new JsonWebTokenProperties();
    jsonWebTokenProperties.setValidity(new JsonWebTokenProperties.Validity(1,2,3));

    return jsonWebTokenProperties;
  }

  @Bean
  public ModelMapper modelMapper(JsonWebTokenProperties jsonWebTokenProperties) {
    return new ModelMapper(jsonWebTokenProperties);
  }
}
