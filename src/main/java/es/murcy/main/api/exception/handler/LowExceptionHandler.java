package es.murcy.main.api.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import static es.murcy.main.api.exception.constants.ErrorMessages.INTERNAL_SERVER_ERROR_ERROR;
import static es.murcy.main.api.exception.constants.ErrorMessages.INTERNAL_SERVER_ERROR_MESSAGE;

import es.murcy.main.api.config.properties.LoggingProperties;
import es.murcy.main.api.exception.ResponseError;

@RestControllerAdvice
@Slf4j
public class LowExceptionHandler extends ResponseEntityExceptionHandler {

  private final boolean debug;
  private final DateTimeFormatter dateTimeFormatter;

  public LowExceptionHandler(
          LoggingProperties loggingProperties,
          @Qualifier("errorDateTimeFormatter") DateTimeFormatter dateTimeFormatter) {
    this.debug = loggingProperties.getErrorResponse().isIncludeStacktrace();
    this.dateTimeFormatter = dateTimeFormatter;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  private ResponseError handleException(Exception exception) {
    return ResponseError.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error(INTERNAL_SERVER_ERROR_ERROR)
            .message(INTERNAL_SERVER_ERROR_MESSAGE)
            .timeStamp(dateTimeFormatter.format(Instant.now()))
            .trace(debug ? ExceptionUtils.getStackTrace(exception) : null)
            .build();
  }

}
