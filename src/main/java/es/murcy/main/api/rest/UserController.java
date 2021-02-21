package es.murcy.main.api.rest;

import es.murcy.main.api.aspect.AuthRequired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = UserController.PATH)
@RestController
@Slf4j
public class UserController {

    public static final String PATH = "/api/user";

    @PostMapping
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String create() {
        return "Not implemented";
    }

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String login() {
        return "Not implemented";
    }

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @AuthRequired(minRol = "test")
    public String getCurrentUserInfo() {
        return "Not implemented";
    }

    @GetMapping("/info/{id}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @AuthRequired
    public String getUserInfoById(@PathVariable Long id, @RequestParam(required = false) String test) {
        return "Not implemented";
    }

    @PutMapping("/info")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String updateCurrentUserInfo() {
        return "Not implemented";
    }

    @PutMapping("/info/{id}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String updateUserInfoById(@PathVariable Long id) {
        return "Not implemented";
    }
}
