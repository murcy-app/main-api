package es.murcy.main.api.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import es.murcy.main.api.config.properties.JsonWebTokenProperties;
import es.murcy.main.api.domain.User;
import es.murcy.main.api.dto.JsonWebTokenDto;
import es.murcy.main.api.dto.UserDto;

@Service
@AllArgsConstructor
public class ModelMapper {

  private final JsonWebTokenProperties jsonWebTokenProperties;

  /**
   * Convert incoming {@link User} into his matching DTO {@link UserDto}
   * @param user Object to transform
   * @return the entity as {@link UserDto}
   */
  public UserDto convertToDto(final User user) {
    return UserDto.builder()
        .id(user.getId())
        .username(user.getUsername())
        .roles(user.getRoles())
        .createdTime(user.getCreatedTime())
        .updatedTime(user.getUpdatedTime()).build();
  }

  /**
   * Builds json web token response DTO {@link JsonWebTokenDto} with incoming value
   * @param jsonWebTokenValue {@link String} value of the token
   * @return the build DTO as {@link JsonWebTokenDto}
   */
  public JsonWebTokenDto buildJsonWebToken(String jsonWebTokenValue) {
    return JsonWebTokenDto.builder()
        .value(jsonWebTokenValue)
        .timeToExpire(calculateTokenValidity(jsonWebTokenProperties.getValidity())).build();
  }

  private Long calculateTokenValidity(final JsonWebTokenProperties.Validity validity) {
    long millis = TimeUnit.MINUTES.toMillis(validity.getMinutes());
    millis += TimeUnit.HOURS.toMillis(validity.getHours());
    millis += TimeUnit.DAYS.toMillis(validity.getDays());

    return millis;
  }
}
