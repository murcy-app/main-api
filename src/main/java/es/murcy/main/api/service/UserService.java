package es.murcy.main.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

import es.murcy.main.api.domain.User;
import es.murcy.main.api.dto.request.UserRequest;
import es.murcy.main.api.repository.UserRepository;
import es.murcy.main.api.service.validators.impl.UserValidator;

@Service
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserValidator validator;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;

    this.validator = new UserValidator(userRepository);
  }

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
