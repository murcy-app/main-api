package es.murcy.main.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = TokenController.PATH)
@Tag(name = "Token")
@RestController
@Slf4j
public class TokenController {

  public static final String PATH = "/api/token";

  @PostMapping(value = "/confirm/user/{token}")
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @Operation(
          summary = "Confirms user in murcy"
  )
  public String confirmUser(@PathVariable String token) {
    return "Not implemented";
  }
}
