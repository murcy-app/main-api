package es.murcy.main.api.exception.exceptions;

import static es.murcy.main.api.exception.constants.ErrorMessages.FORBIDDEN_MESSAGE;

public class ForbiddenException extends RuntimeException {
  private static final long serialVersionUID = -4577521687810087066L;

  public ForbiddenException() {
    super(FORBIDDEN_MESSAGE);
  }

  public ForbiddenException(final String message) {
    super(message);
  }
}
