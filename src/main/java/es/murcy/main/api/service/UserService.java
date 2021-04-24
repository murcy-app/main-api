package es.murcy.main.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

import es.murcy.main.api.config.jwt.JwtUserDetailsService;
import es.murcy.main.api.domain.User;
import es.murcy.main.api.dto.request.UserLoginRequest;
import es.murcy.main.api.dto.request.UserRequest;
import es.murcy.main.api.exception.exceptions.ForbiddenException;
import es.murcy.main.api.repository.UserRepository;
import es.murcy.main.api.service.validators.impl.UserValidator;

@Service
@RequiredArgsConstructor
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @Qualifier("userValidator")
  private final UserValidator validator;
  private final JwtUserDetailsService jwtUserDetailsService;
  private final JsonWebTokenService jsonWebTokenService;

  public User createEntity(final UserRequest userRequest, final User.Rol maxRol) {

    validator.validateCreate(userRequest);

    final User user = new User();

    user.setUsername(userRequest.getUsername());
    user.setEmail(userRequest.getEmail());
    user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    user.setCreatedTime(Instant.now());
    user.setUpdatedTime(Instant.now());

    setUsersAuthProperties(userRequest, user, maxRol);

    return userRepository.save(user);
  }

  public String authenticateUser(final UserLoginRequest request) {
    User user = userRepository.findUserByUsername(request.getUser()).orElseThrow(ForbiddenException::new);

    if(user.getConfirmed()) {
      throw new ForbiddenException();
    }

    final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getUser());

    return jsonWebTokenService.generateToken(userDetails);
  }

  private void setUsersAuthProperties(final UserRequest userRequest, final User user, final User.Rol maxRol) {
    if(maxRol.ordinal() >= User.Rol.ADMINISTRATOR.ordinal()) {
      user.setRoles(userRequest.getRoles());
      user.setConfirmed(userRequest.getSendMail());
    } else {
      user.setRoles(Collections.singleton(User.Rol.NOT_TRACKED));
      user.setConfirmed(Boolean.FALSE);
      userRequest.setSendMail(false);
    }
  }

  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  public Optional<User> findByUserName(String username) {
    return userRepository.findUserByUsername(username);
  }

}
