package ru.flystar.travelrk.service;

import org.springframework.stereotype.Service;
import ru.flystar.travelrk.domain.persistents.User;
import ru.flystar.travelrk.repositories.UserRepository;

@Service("userService")
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
