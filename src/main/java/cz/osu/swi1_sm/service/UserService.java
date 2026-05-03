package cz.osu.swi1_sm.service;

import cz.osu.swi1_sm.model.dto.UserToken;
import cz.osu.swi1_sm.model.entity.AppUser;
import cz.osu.swi1_sm.model.entity.Role;
import cz.osu.swi1_sm.model.repository.AppUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final AppUserRepository userRepository;

    public UserService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserToken register(String name, String email, String password) {
        if (userRepository.existsByUsernameIgnoreCase(email)) {
            throw new IllegalArgumentException("User with this email already exists.");
        }
        AppUser user = new AppUser();
        user.setUsername(email);
        user.setPassword(password);
        user.setRole(Role.MEMBER);
        userRepository.save(user);
        return new UserToken(name, Role.MEMBER);
    }

    public UserToken login(String email, String password) {
        AppUser user = userRepository.findByUsernameIgnoreCase(email);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return new UserToken(user.getUsername(), user.getRole());
            } else {
                throw new RuntimeException("Wrong password!");
            }
        } else {
            throw new IllegalArgumentException("User does not exist!");
        }
    }
}