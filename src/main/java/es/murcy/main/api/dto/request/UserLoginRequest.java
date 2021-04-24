package es.murcy.main.api.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = {"password"})
@NoArgsConstructor
public class UserLoginRequest {

  private String user;
  private String password;
}
