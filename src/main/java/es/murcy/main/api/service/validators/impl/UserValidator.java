package es.murcy.main.api.service.validators.impl;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.*;

import static es.murcy.main.api.service.validators.ValidationConstants.*;

import es.murcy.main.api.dto.request.UserRequest;
import es.murcy.main.api.exception.exceptions.ModelValidationException;
import es.murcy.main.api.repository.UserRepository;
import es.murcy.main.api.service.validators.ValidateCreate;
import es.murcy.main.api.service.validators.ValidateUpdate;

@AllArgsConstructor
public class UserValidator implements ValidateCreate<UserRequest>, ValidateUpdate<UserRequest> {

  public static final List<String> NOT_VALID_USERNAMES =
      Arrays.asList("admin", "administrator", "root", "owner");

  private final UserRepository userRepository;

  @Override
  public void validateCreate(UserRequest entity) {
    Map<String, String> errors = new HashMap<>();

    validateUsername(entity.getUsername(), errors, true);
    validateEmail(entity.getEmail(), errors, true);
    validatePassword(entity.getPassword(), errors);

    if(!errors.isEmpty()) {
      throw new ModelValidationException(errors);
    }
  }

  @Override
  public void validateUpdate(UserRequest entity) {
    Map<String, String> errors = new HashMap<>();

    validateUsername(entity.getUsername(), errors, false);
    validateEmail(entity.getEmail(), errors, false);
    validatePassword(entity.getPassword(), errors);

    if(!errors.isEmpty()) {
      throw new ModelValidationException(errors);
    }
  }

  private void validateUsername(final String username, final Map<String, String> errors, final boolean evaluateEmpty) {
    final String fieldName = "username";

    if(StringUtils.isEmpty(username) && evaluateEmpty) {
      errors.put(fieldName, PROPERTY_IS_EMPTY);
      return;
    }

    if(NOT_VALID_USERNAMES.contains(username.toLowerCase(Locale.ROOT))) {
      errors.put(fieldName, PROPERTY_IS_NOT_ALLOWED);
      return;
    }

    if(userRepository.existsUserByUsername(username)) {
      errors.put(fieldName, PROPERTY_IS_NOT_UNIQUE);
    }
  }

  private void validateEmail(final String email, final Map<String, String> errors, final boolean evaluateEmpty) {
    final String fieldName = "email";

    if(StringUtils.isEmpty(email) && evaluateEmpty) {
      errors.put(fieldName, PROPERTY_IS_EMPTY);
      return;
    }

    try {
      InternetAddress emailObject = new InternetAddress(email);
      emailObject.validate();
    } catch (AddressException ignored) {
      errors.put(fieldName, PROPERTY_IS_NOT_VALID);
    }

    if(userRepository.existsUserByEmail(email)) {
      errors.put(fieldName, PROPERTY_IS_NOT_UNIQUE);
    }
  }

  private void validatePassword(final String password, final Map<String, String> errors) {
    final String fieldName = "password";

    if(StringUtils.isEmpty(password)) {
      errors.put(fieldName, PROPERTY_IS_EMPTY);
    }
  }
}
