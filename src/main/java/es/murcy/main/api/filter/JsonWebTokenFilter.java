package es.murcy.main.api.filter;

import es.murcy.main.api.config.jwt.JsonWebTokenService;
import es.murcy.main.api.config.jwt.JwtUserDetailsService;
import es.murcy.main.api.exception.exceptions.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(JsonWebTokenFilter.ORDER)
@AllArgsConstructor
@Slf4j
public class JsonWebTokenFilter extends OncePerRequestFilter {

  public static final int ORDER = Ordered.LOWEST_PRECEDENCE - 10;

  private final JwtUserDetailsService jwtUserDetailsService;
  private final JsonWebTokenService jsonWebTokenService;
  private final HandlerExceptionResolver handlerExceptionResolver;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    final String authorization = request.getHeader("Authorization");
    String username = null;
    String token;

    if(StringUtils.hasLength(authorization) && authorization.startsWith("Bearer ")) {
      token = authorization.substring(7);
      try {
        username = jsonWebTokenService.getUserNameFromToken(token);
      } catch (ExpiredJwtException e) {
        handlerExceptionResolver.resolveException(request, response, null,
                new UnauthorizedException("Authentication has expired"));
      } catch (MalformedJwtException e) {
        handlerExceptionResolver.resolveException(request, response, null,
                new UnauthorizedException("Authentication has incorrect format"));
      } catch (SignatureException | IllegalArgumentException e) {
        handlerExceptionResolver.resolveException(request, response, null,
                new UnauthorizedException());
      }
    }

    UserDetails userDetails = null;
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      userDetails = jwtUserDetailsService.loadUserByUsername(username);
    }

    if(userDetails != null) {
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
      usernamePasswordAuthenticationToken
              .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    filterChain.doFilter(request, response);
  }
}
