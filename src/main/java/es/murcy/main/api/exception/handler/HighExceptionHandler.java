package es.murcy.main.api.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import static es.murcy.main.api.exception.constants.ErrorMessages.BAD_REQUEST_MESSAGE;
import static es.murcy.main.api.exception.constants.ErrorMessages.UNAUTHORIZED_ERROR;

import es.murcy.main.api.config.properties.LoggingProperties;
import es.murcy.main.api.exception.ResponseError;
import es.murcy.main.api.exception.exceptions.ModelValidationException;
import es.murcy.main.api.exception.exceptions.UnauthorizedException;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HighExceptionHandler {

  private final boolean debug;
  private final DateTimeFormatter dateTimeFormatter;

  public HighExceptionHandler(
          LoggingProperties loggingProperties,
          @Qualifier("errorDateTimeFormatter") DateTimeFormatter dateTimeFormatter) {
    this.debug = loggingProperties.getErrorResponse().isIncludeStacktrace();
    this.dateTimeFormatter = dateTimeFormatter;
  }

  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  private ResponseError handleUnauthorizedException(UnauthorizedException unauthorizedException) {
    return ResponseError.builder()
            .status(HttpStatus.UNAUTHORIZED.value())
            .error(UNAUTHORIZED_ERROR)
            .message(unauthorizedException.getMessage())
            .timeStamp(dateTimeFormatter.format(Instant.now()))
            .trace(debug ? ExceptionUtils.getStackTrace(unauthorizedException) : null)
            .build();
  }

  @ExceptionHandler(ModelValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseError handle(ModelValidationException modelValidationException) {
    String message = modelValidationException.getErrors()
        .entrySet().stream()
        .map(entry -> String.format(entry.getValue(), entry.getKey()))
        .collect(Collectors.joining(", "));

    return ResponseError.builder()
        .status(HttpStatus.BAD_REQUEST.value())
        .error(BAD_REQUEST_MESSAGE)
        .message(message)
        .timeStamp(dateTimeFormatter.format(Instant.now()))
        .trace(debug ? ExceptionUtils.getStackTrace(modelValidationException) : null)
        .build();
  }
}
