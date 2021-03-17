package es.murcy.main.api.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;

import static es.murcy.main.api.utils.TestJsonUtils.toJson;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import es.murcy.main.api.config.TestData;
import es.murcy.main.api.config.TestSecurityConfig;
import es.murcy.main.api.config.WebConfiguration;
import es.murcy.main.api.domain.User;
import es.murcy.main.api.dto.request.UserRequest;
import es.murcy.main.api.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserController.class, TestSecurityConfig.class, WebConfiguration.class, TestData.class})
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  public void givenValidUserCreateRequestWhenHandlingRequestThenReturnCreatedUser() throws Exception {
     final UserRequest request = new UserRequest();
    request.setUsername("test");
    request.setPassword("test");
    request.setEmail("test@test.es");
    final User user = new User();
    user.setId(-1L);
    user.setUsername("test");
    user.setEmail("test@test.es");
    user.setCreatedTime(Instant.now());
    user.setUpdatedTime(Instant.now());
    user.setRoles(Collections.singleton(User.Rol.NOT_TRACKED));

    when(userService.createEntity(eq(request), eq(User.Rol.NOT_TRACKED))).thenReturn(user);

    mockMvc.perform(post(UserController.PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJson(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(-1)))
        .andExpect(jsonPath("$.username", is("test")))
        .andExpect(jsonPath("$.password").doesNotExist())
        .andExpect(jsonPath("$.email", is("test@test.es")))
        .andExpect(jsonPath("$.roles", hasSize(1)))
        .andExpect(jsonPath("$.roles[0]", is("NOT_TRACKED")))
        .andExpect(jsonPath("$.createdTime").exists())
        .andExpect(jsonPath("$.updatedTime").exists());
  }
}
