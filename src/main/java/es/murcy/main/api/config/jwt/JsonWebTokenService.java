package es.murcy.main.api.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JsonWebTokenService implements Serializable {

    private static final long serialVersionUID = -7615925694794523015L;

    private final long jwtValidityDays;
    private final long jwtValidityHours;
    private final long jwtValidityMinutes;
    private final String jwtSecret;

    public JsonWebTokenService(
            @Value("${security.jwt.validity.days:1}") long jwtValidityDays,
            @Value("${security.jwt.validity.hours:0}") long jwtValidityHours,
            @Value("${security.jwt.validity.minutes:0}") long jwtValidityMinutes,
            @Value("${security.jwt.secret}") String jwtSecret) {
        this.jwtValidityDays = jwtValidityDays;
        this.jwtValidityHours = jwtValidityHours;
        this.jwtValidityMinutes = jwtValidityMinutes;
        this.jwtSecret = jwtSecret;
    }

    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(
                        Instant.now()
                                .plus(jwtValidityDays, ChronoUnit.DAYS)
                                .plus(jwtValidityHours, ChronoUnit.HOURS)
                                .plus(jwtValidityMinutes, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

}
