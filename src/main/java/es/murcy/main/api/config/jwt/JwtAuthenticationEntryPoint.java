package es.murcy.main.api.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.murcy.main.api.config.properties.LoggingProperties;
import es.murcy.main.api.exception.ResponseError;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import static es.murcy.main.api.exception.constants.ErrorMessages.UNAUTHORIZED_ERROR;
import static es.murcy.main.api.exception.constants.ErrorMessages.UNAUTHORIZED_MESSAGE;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

  private static final long serialVersionUID = 778200216891297715L;

  private final boolean debug;
  private final DateTimeFormatter dateTimeFormatter;

  public JwtAuthenticationEntryPoint(
          LoggingProperties loggingProperties,
          @Qualifier("errorDateTimeFormatter") DateTimeFormatter dateTimeFormatter) {
    this.debug = loggingProperties.getErrorResponse().isIncludeStacktrace();
    this.dateTimeFormatter = dateTimeFormatter;
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
                       AuthenticationException authException) throws IOException {
    ResponseError responseError = ResponseError.builder()
            .status(HttpStatus.UNAUTHORIZED.value())
            .error(UNAUTHORIZED_ERROR)
            .message(UNAUTHORIZED_MESSAGE)
            .timeStamp(dateTimeFormatter.format(Instant.now()))
            .trace(debug ? "JwtAuthenticationEntryPoint#commence" : null)
            .build();

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(new ObjectMapper().writeValueAsString(responseError));
    response.getWriter().flush();
  }
}
