package es.murcy.main.api.exception.handler;

import es.murcy.main.api.exception.ResponseError;
import es.murcy.main.api.exception.exceptions.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import static es.murcy.main.api.exception.constants.ErrorMessages.*;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HighExceptionHandler {

  private final boolean debug;
  private final DateTimeFormatter dateTimeFormatter;

  public HighExceptionHandler(
          @Value("${feature.error.include-stacktrace:false}") boolean debug,
          @Qualifier("errorDateTimeFormatter") DateTimeFormatter dateTimeFormatter) {
    this.debug = debug;
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

}
