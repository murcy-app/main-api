package es.murcy.main.api.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import es.murcy.main.api.config.properties.JsonWebTokenProperties;
import es.murcy.main.api.domain.User;
import es.murcy.main.api.dto.TokenDto;
import es.murcy.main.api.dto.UserDto;

@Service
@AllArgsConstructor
public class ModelMapper {

  private final JsonWebTokenProperties jsonWebTokenProperties;

  private UserDto convertToDto(final User user) {
    return UserDto.builder()
        .id(user.getId())
        .username(user.getUsername())
        .roles(user.getRoles())
        .createdTime(user.getCreatedTime())
        .updatedTime(user.getUpdatedTime()).build();
  }

  private TokenDto convertToDto() {
    return TokenDto.builder()
        .timeToExpire(calcTokenValidity(jsonWebTokenProperties.getValidity())).build();
  }

  private Long calcTokenValidity(final JsonWebTokenProperties.Validity validity) {
    long millis = TimeUnit.MINUTES.toMillis(validity.getMinutes());
    millis += TimeUnit.HOURS.toMillis(validity.getHours());
    millis += TimeUnit.DAYS.toMillis(validity.getDays());

    return millis;
  }
}
