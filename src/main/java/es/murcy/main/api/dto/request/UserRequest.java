package es.murcy.main.api.dto.request;

import es.murcy.main.api.domain.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@ToString(exclude = {"password"})
@NoArgsConstructor
public class UserRequest {

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @NotBlank
  @Email
  private String email;

  private Set<User.Rol> roles;

  private Boolean sendMail;
}
