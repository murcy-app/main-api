package es.murcy.main.api.exception.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessages {

  public static final String INTERNAL_SERVER_ERROR_ERROR = "Internal server error";
  public static final String INTERNAL_SERVER_ERROR_MESSAGE = "The server had unexpected behavior while processing the request";

  public static final String UNAUTHORIZED_ERROR = "Unauthorized";
  public static final String UNAUTHORIZED_MESSAGE = "User is not authorized in the system";

  public static final String NOT_FOUND_ERROR = "Not found";
  public static final String NOT_FOUND_MESSAGE = "Item not found";

  public static final String FORBIDDEN_ERROR = "Forbidden";
  public static final String FORBIDDEN_MESSAGE = "User has not access to this content";


}
