package es.murcy.main.api.exception.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class ModelValidationException extends RuntimeException {
  private static final long serialVersionUID = -7365252241702874125L;

  private final Map<String, String> errors;

  public ModelValidationException(final Map<String, String> errors) {
    this.errors = errors;
  }

}
