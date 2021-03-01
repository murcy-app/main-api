package es.murcy.main.api.aspect.impl;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

import es.murcy.main.api.aspect.AuthRequired;
import es.murcy.main.api.util.AspectUtils;

@Aspect
@Component
@Slf4j
public class AuthRequiredAspect {

  @Around("@annotation(es.murcy.main.api.aspect.AuthRequired)")
  public Object validateAuth(ProceedingJoinPoint joinPoint) throws Throwable {
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();

    Map<String, Object> arguments = AspectUtils.getArgumentMap(joinPoint);

    AuthRequired authRequired = method.getAnnotation(AuthRequired.class);

    // TODO: add logic

    return joinPoint.proceed();
  }


}
