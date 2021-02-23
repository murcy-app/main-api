package es.murcy.main.api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AspectUtils {

  public static Map<String, Object> getArgumentMap(@NonNull ProceedingJoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    List<String> parameterNames = Arrays.asList(signature.getParameterNames());
    List<Object> parameterValues = Arrays.asList(joinPoint.getArgs());

    return IntStream.range(0, parameterNames.size()).boxed()
            .collect(HashMap::new, (map, index) ->
                    map.put(parameterNames.get(index), parameterValues.get(index)), HashMap::putAll);
  }
}
