package es.murcy.main.api.rest;

import es.murcy.main.api.aspect.AuthRequired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = UserController.PATH)
@Tag(name = "User")
@RestController
@Slf4j
public class UserController {

  public static final String PATH = "/api/user";

  @PostMapping
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @Operation(
          summary = "Creates new user in Murcy"
  )
  public String create() {
    return "Not implemented";
  }

  @PostMapping(value = "/login")
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @Operation(
          summary = "Create a session for a Murcy user"
  )
  public String login() {
    return "Not implemented";
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
