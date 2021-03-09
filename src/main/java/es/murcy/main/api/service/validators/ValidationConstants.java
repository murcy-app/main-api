package es.murcy.main.api.service.validators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationConstants {

  public static final String PROPERTY_IS_EMPTY = "'%s' cannot be empty";
  public static final String PROPERTY_IS_NOT_ALLOWED = "'%s' value is not allowed";
  public static final String PROPERTY_IS_NOT_VALID = "'%s' value is not valid";
  public static final String PROPERTY_IS_NOT_UNIQUE = "'%s' already exists in the system";

}
