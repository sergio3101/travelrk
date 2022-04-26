package ru.flystar.travelrk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flystar.travelrk.domain.persistents.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  User findByLogin(String login);
}
