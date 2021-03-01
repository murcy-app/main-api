package es.murcy.main.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import es.murcy.main.api.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Boolean existsUserByUsername(final String username);
  Boolean existsUserByEmail(final String email);

  Optional<User> findUserByUsername(String username);

}
