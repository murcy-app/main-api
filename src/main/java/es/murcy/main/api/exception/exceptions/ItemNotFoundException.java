package es.murcy.main.api.exception.exceptions;

import static es.murcy.main.api.exception.constants.ErrorMessages.NOT_FOUND_MESSAGE;

public class ItemNotFoundException extends RuntimeException {
  private static final long serialVersionUID = -142518394495736516L;

  public ItemNotFoundException() {
    super(NOT_FOUND_MESSAGE);
  }

  public ItemNotFoundException(final String message) {
    super(message);
  }
}
