package es.murcy.main.api.filter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.murcy.main.api.service.JsonWebTokenService;
import es.murcy.main.api.domain.User;
import es.murcy.main.api.exception.exceptions.ForbiddenException;
import es.murcy.main.api.exception.exceptions.ItemNotFoundException;
import es.murcy.main.api.exception.exceptions.UnauthorizedException;
import es.murcy.main.api.controller.UserController;
import es.murcy.main.api.service.UserService;

@Component
@Slf4j
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserControllerFilter extends OncePerRequestFilter {

  private static final String FILTER_URL_PATTERN = UserController.PATH.concat("/info/(?<id>\\d+)");

  private final UserService userService;
  private final JsonWebTokenService jsonWebTokenService;
  private final HandlerExceptionResolver handlerExceptionResolver;

  @Override
  protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws IOException, ServletException {
    final String authorization = request.getHeader("Authorization");
    if (authorization != null) {
      try {
        final String username = jsonWebTokenService.getUserNameFromToken(authorization.substring(7));
        User requester = userService.findByUserName(username).orElseThrow(UnauthorizedException::new);
        User user = userService.findById(getId(request)).orElseThrow(() -> new ItemNotFoundException("User not found"));

        if (!requester.getId().equals(user.getId())) {
          handlerExceptionResolver.resolveException(request, response, null, new ForbiddenException());
        }
      } catch (Exception ignored) {}
    }

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    return !path.matches(FILTER_URL_PATTERN);
  }

  private Long getId(final HttpServletRequest request){
    String path = request.getRequestURI();
    Matcher matcher = Pattern.compile(FILTER_URL_PATTERN).matcher(path);
    if(matcher.find()) {
      return Long.parseLong(matcher.group("id"));
    }
    return -1L;
  }
}
