package es.murcy.main.api.exception.handler;

import es.murcy.main.api.exception.ResponseError;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = CustomErrorController.PATH)
public class CustomErrorController implements ErrorController {

  public static final String PATH = "/error";

  private final ErrorAttributes errorAttributes;
  private final DateTimeFormatter dateTimeFormatter;
  private final boolean debug;

  public CustomErrorController(
          ErrorAttributes errorAttributes,
          @Value("${feature.error.include-stacktrace:false}") boolean debug,
          @Qualifier("errorDateTimeFormatter") DateTimeFormatter dateTimeFormatter) {
    Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
    this.errorAttributes = errorAttributes;
    this.dateTimeFormatter = dateTimeFormatter;
    this.debug = debug;
  }

  @RequestMapping()
  ResponseError handleError(HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> errorMap = getErrorAttributes(request, debug);

    return ResponseError.builder()
            .status(response.getStatus())
            .error((String) errorMap.get("error"))
            .message(dateTimeFormatter.format(((Date) errorMap.get("timestamp")).toInstant()))
            .trace((String) errorMap.get("trace"))
            .build();
  }

  @Override
  public String getErrorPath() {
    return null;
  }

  private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
    WebRequest webRequest = new ServletWebRequest(request);

    ErrorAttributeOptions options = ErrorAttributeOptions.defaults();
    options = options.including(ErrorAttributeOptions.Include.EXCEPTION);
    options = options.including(ErrorAttributeOptions.Include.MESSAGE);
    options = options.including(ErrorAttributeOptions.Include.BINDING_ERRORS);

    if(includeStackTrace) {
      options = options.including(ErrorAttributeOptions.Include.STACK_TRACE);
    }

    return errorAttributes.getErrorAttributes(webRequest, options);
  }
}
