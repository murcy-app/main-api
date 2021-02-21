package es.murcy.main.api.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = UserController.PATH)
@RestController
@Slf4j
public class TokenController {

    public static final String PATH = "/api/token";

    @PostMapping(value = "/confirm/user/{token}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String confirmUser(@PathVariable String token) {
        return "Not implemented";
    }
}
