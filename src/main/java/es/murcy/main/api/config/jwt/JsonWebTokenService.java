package es.murcy.main.api.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import es.murcy.main.api.config.properties.JsonWebTokenProperties;

@Component
public class JsonWebTokenService implements Serializable {

  private static final long serialVersionUID = -7615925694794523015L;

  private final long jwtValidityDays;
  private final long jwtValidityHours;
  private final long jwtValidityMinutes;
  private final String secret;

  public JsonWebTokenService(JsonWebTokenProperties jsonWebTokenProperties) {
    this.jwtValidityDays = jsonWebTokenProperties.getValidity().getDays();
    this.jwtValidityHours = jsonWebTokenProperties.getValidity().getHours();
    this.jwtValidityMinutes = jsonWebTokenProperties.getValidity().getMinutes();
    this.secret = jsonWebTokenProperties.getSecret();
  }

  public String getUserNameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return doGenerateToken(claims, userDetails.getUsername());
  }

  private String doGenerateToken(Map<String, Object> claims, String subject) {
    return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date.from(Instant.now())).setExpiration(Date.from(Instant.now().plus(jwtValidityDays, ChronoUnit.DAYS).plus(jwtValidityHours, ChronoUnit.HOURS).plus(jwtValidityMinutes, ChronoUnit.MINUTES))).signWith(SignatureAlgorithm.HS512, secret).compact();
  }

}
