package es.murcy.main.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonPropertyOrder({"status, error, message, timeStamp, trace"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseError {

  private final Integer status;
  private final String error;
  private final String message;
  private final String timeStamp;

  @Schema(hidden = true)
  private final String trace;
}
