package es.murcy.main.api.config;

import es.murcy.main.api.config.jwt.JwtAuthenticationEntryPoint;
import es.murcy.main.api.config.jwt.JwtUserDetailsService;
import es.murcy.main.api.filter.JwtFilter;
import es.murcy.main.api.rest.TokenController;
import es.murcy.main.api.rest.UserController;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final JwtFilter jwtFilter;
  private final JwtUserDetailsService jwtUserDetailsService;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();

  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {

    httpSecurity.authorizeRequests()
            .antMatchers("/swagger-ui/**").permitAll()
            .antMatchers("/docs/**").permitAll()
            .antMatchers(UserController.PATH).permitAll()
            .antMatchers(UserController.PATH.concat("/login")).permitAll()
            .antMatchers(TokenController.PATH.concat("/confirm/user/**")).permitAll()
            .anyRequest().authenticated();

    httpSecurity.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
    httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    httpSecurity.cors();
    httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    httpSecurity.httpBasic();
    httpSecurity.csrf().disable();
    httpSecurity.headers().frameOptions().sameOrigin();
  }

}