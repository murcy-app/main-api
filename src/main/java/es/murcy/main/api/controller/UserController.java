package es.murcy.main.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import es.murcy.main.api.aspect.AuthRequired;
import es.murcy.main.api.domain.User;
import es.murcy.main.api.dto.UserDto;
import es.murcy.main.api.dto.mapper.ModelMapper;
import es.murcy.main.api.dto.request.UserRequest;
import es.murcy.main.api.service.UserService;

@RequestMapping(path = UserController.PATH)
@Tag(name = "User")
@RestController
@Slf4j
@AllArgsConstructor
public class UserController {

  public static final String PATH = "/api/user";

  private final UserService userService;
  private final ModelMapper mapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(
          summary = "Creates new user in Murcy"
  )
  public UserDto create(@RequestBody UserRequest userRequest) {
    return mapper.convertToDto(
        userService.createEntity(userRequest, User.Rol.NOT_TRACKED)
    );
  }

  @PostMapping(value = "/login")
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @Operation(
          summary = "Create a session for a Murcy user"
  )
  public String login(@RequestParam String test) {
    return "Not implemented";
  }

  @GetMapping("/info")
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @Operation(
          summary = "Get current user data",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  public String getCurrentUserInfo() {
    return "Not implemented";
  }

  @GetMapping("/info/{id}")
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @Operation(
          summary = "Get user data by identifier",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  public String getUserInfoById(@PathVariable Long id) {
    return "Not implemented";
  }

  @PutMapping("/info")
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @Operation(
          summary = "Update current user data",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  public String updateCurrentUserInfo() {
    return "Not implemented";
  }

  @PutMapping("/info/{id}")
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @Operation(
          summary = "Update user data by identifier",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  public String updateUserInfoById(@PathVariable Long id) {
    return "Not implemented";
  }
}
