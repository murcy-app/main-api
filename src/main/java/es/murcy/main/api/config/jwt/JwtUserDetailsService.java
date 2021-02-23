package es.murcy.main.api.config.jwt;

import es.murcy.main.api.domain.User;
import es.murcy.main.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
@Component
public class JwtUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> systemUser = userRepository.findUserByUsername(username);

    return systemUser.map(user -> new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            //user.getConfirmed(),
            true,
            true,
            true,
            true,
            new ArrayList<>())
    ).orElse(null);
  }
}
