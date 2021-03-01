package es.murcy.main.api.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import java.util.Set;

import es.murcy.main.api.domain.User;

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
