package es.murcy.main.api.exception.exceptions;

import static es.murcy.main.api.exception.constants.ErrorMessages.UNAUTHORIZED_MESSAGE;

public class UnauthorizedException extends RuntimeException {
  private static final long serialVersionUID = 8527284268097515264L;

  public UnauthorizedException() {
    super(UNAUTHORIZED_MESSAGE);
  }

  public UnauthorizedException(final String message) {
    super(message);
  }
}
