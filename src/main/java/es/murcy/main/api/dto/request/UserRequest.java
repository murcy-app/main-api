package es.murcy.main.api.dto.request;

import es.murcy.main.api.domain.User;
import lombok.*;

import javax.validation.constraints.Email;
import java.util.Set;

@Data
@ToString(exclude = {"password"})
@NoArgsConstructor
public class UserRequest {

  private String username;
  private String password;
  @Email
  private String email;

  private Set<User.Rol> roles;
  private Boolean sendMail;
}
