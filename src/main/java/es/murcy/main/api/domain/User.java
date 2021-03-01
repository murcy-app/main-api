package es.murcy.main.api.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

import es.murcy.main.api.domain.converters.CollectionRolToStringConverter;

@Entity
@Table(name = "MURCY_USER")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"password"})
@Getter
@Setter
public class User {

  public enum Rol {
    NOT_TRACKED, USER, EDITOR, REVIEWER, ADMINISTRATOR
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ID", unique = true, nullable = false)
  private Long id;

  @Column(name = "USERNAME", unique = true, nullable = false)
  private String username;

  @Column(name = "EMAIL", unique = true, nullable = false)
  private String email;

  @Column(name = "PASSWORD", nullable = false, length = 512)
  private String password;

  @Column(name = "USER_ROLES", nullable = false, length = 512)
  @Convert(converter = CollectionRolToStringConverter.class)
  private Set<User.Rol> roles;

  @Column(name = "DATE_CREATED", nullable = false)
  private Instant createdTime;

  @Column(name = "DATE_UPDATED", nullable = false)
  private Instant updatedTime;

  @Column(name = "DATE_LAST_LOGIN")
  private Instant lastLoginTime;

  @Column(name = "CONFIRMED")
  private Boolean confirmed;
}
