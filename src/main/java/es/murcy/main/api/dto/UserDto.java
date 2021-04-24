package es.murcy.main.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

import es.murcy.main.api.domain.User;

@JsonPropertyOrder({"id, username, email, roles, createdTime, updatedTime"})
@Data
@Builder
public class UserDto {

  private Long id;

  private String username;

  private String email;

  private Set<User.Rol> roles;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
  private Instant createdTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
  private Instant updatedTime;
}
