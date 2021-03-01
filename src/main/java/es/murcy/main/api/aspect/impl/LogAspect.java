package es.murcy.main.api.aspect.impl;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static es.murcy.main.api.util.StringUtils.DOT;
import static es.murcy.main.api.util.StringUtils.SPACE;

import es.murcy.main.api.config.properties.LoggingProperties;
import es.murcy.main.api.util.AspectUtils;

@Aspect
@Component
@Slf4j
public class LogAspect {

  private final boolean logClassnameAndMethod;

  public LogAspect(LoggingProperties loggingProperties) {
    this.logClassnameAndMethod = loggingProperties.getController().isClassnameMethod();
  }

  @Around("execution(public * es.murcy.main.api.rest.*Controller.*(..))")
  public Object logControllers(ProceedingJoinPoint joinPoint) throws Throwable {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    String methodName = signature.getName();
    String classname = signature.getDeclaringTypeName();

    Map<String, Object> arguments = AspectUtils.getArgumentMap(joinPoint);

    StringBuilder logMessage = new StringBuilder();

    if (logClassnameAndMethod) {
      logMessage.append(classname).append(DOT).append(methodName).append("() ");
    }
    logMessage.append(request.getMethod()).append(SPACE).append(request.getServletPath());
    logMessage.append(" with arguments: '").append(arguments).append("'");

    LOG.info(logMessage.toString());

    return joinPoint.proceed();
  }
}
