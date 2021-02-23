package es.murcy.main.api.rest;

import es.murcy.main.api.aspect.AuthRequired;
import es.murcy.main.api.config.jwt.JsonWebTokenService;
import es.murcy.main.api.config.jwt.JwtUserDetailsService;
import es.murcy.main.api.domain.User;
import es.murcy.main.api.dto.request.UserRequest;
import es.murcy.main.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = UserController.PATH)
@Tag(name = "User")
@RestController
@Slf4j
@AllArgsConstructor
public class UserController {

  public static final String PATH = "/api/user";

  private final UserService userService;
  private final JwtUserDetailsService jwtUserDetailsService;
  private final JsonWebTokenService jsonWebTokenService;

  @PostMapping
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @Operation(
          summary = "Creates new user in Murcy"
  )
  public User create(@RequestBody UserRequest userRequest) {
    return userService.createEntity(userRequest, User.Rol.NOT_TRACKED);
  }

  @PostMapping(value = "/login")
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @Operation(
          summary = "Create a session for a Murcy user"
  )
  public String login(@RequestParam String test) {
    UserDetails user = jwtUserDetailsService.loadUserByUsername(test);
    return jsonWebTokenService.generateToken(user);
  }

  @GetMapping("/info")
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @AuthRequired(minRol = "test")
  @Operation(
          summary = "Get current user data",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  public String getCurrentUserInfo() {
    return "Not implemented";
  }

  @GetMapping("/info/{id}")
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @AuthRequired
  @Operation(
          summary = "Get user data by identifier",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  public String getUserInfoById(@PathVariable Long id) {
    return "Not implemented";
  }

  @PutMapping("/info")
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @AuthRequired
  @Operation(
          summary = "Update current user data",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  public String updateCurrentUserInfo() {
    return "Not implemented";
  }

  @PutMapping("/info/{id}")
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @AuthRequired
  @Operation(
          summary = "Update user data by identifier",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  public String updateUserInfoById(@PathVariable Long id) {
    return "Not implemented";
  }
}
