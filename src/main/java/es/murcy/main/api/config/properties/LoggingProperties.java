package es.murcy.main.api.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "feature.logging")
@NoArgsConstructor
@Data
public class LoggingProperties {

  /**
   * Contains all custom features logging errors
   */
  private ErrorResponseProperties errorResponse = new ErrorResponseProperties();

  /**
   * Contains all custom features logging controllers
   */
  private LogControllers controller = new LogControllers();

  @NoArgsConstructor
  @Data
  public static class ErrorResponseProperties {

    /**
     * If enabled, the stacktrace will be included in the error responses.
     * It's <b>high</b> recommended not enable this in production environments
     */
    private boolean includeStacktrace = false;
  }

  @NoArgsConstructor
  @Data
  public static class LogControllers {

    /**
     * If enabled, logs will contain the classname and the method that contains the endpoint handler.
     */
    private boolean classnameMethod = false;
  }
}
