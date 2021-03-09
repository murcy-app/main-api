package es.murcy.main.api.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "MURCY_TOKEN")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  @Column(name = "TOKEN_ID", unique = true, nullable = false)
  private Long id;

  @Column(name = "VALUE", nullable = false)
  private String value;

  @Column(name = "CLASSNAME", unique = true, nullable = false)
  private String classname;

  @Column(name = "DATE_EXPIRE", nullable = false)
  private Instant expiration;

  @OneToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "OWNER_ID")
  private User owner;
}
