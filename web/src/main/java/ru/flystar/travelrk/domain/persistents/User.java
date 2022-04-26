package ru.flystar.travelrk.domain.persistents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseId {
  @Column(name = "fio", nullable = false, length = 45)
  private String fio;
  @Column(name = "login", nullable = false, length = 45)
  private String login;
}
