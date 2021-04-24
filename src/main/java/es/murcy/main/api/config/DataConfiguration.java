package es.murcy.main.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.murcy.main.api.repository.UserRepository;
import es.murcy.main.api.service.validators.impl.UserValidator;

@Configuration
@RequiredArgsConstructor
public class DataConfiguration {

  final UserRepository userRepository;

  @Bean
  public UserValidator userValidator() {
    return new UserValidator(userRepository);
  }
}
