package cz.osu.swi1_sm.service;

import cz.osu.swi1_sm.model.dto.UserToken;
import cz.osu.swi1_sm.model.entity.AppUser;
import cz.osu.swi1_sm.model.entity.Role;
import cz.osu.swi1_sm.model.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private AppUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void login_validCredentials_returnsUserToken() {
        AppUser user = new AppUser();
        user.setEmail("student@osu.cz");
        user.setPassword("heslo");
        user.setRole(Role.MEMBER);

        when(userRepository.findByEmailIgnoreCase("student@osu.cz")).thenReturn(user);

        UserToken token = userService.login("student@osu.cz", "heslo");

        assertEquals("student@osu.cz", token.getName());
        assertEquals(Role.MEMBER, token.getRole());
    }

    @Test
    void login_wrongPassword_throwsException() {
        AppUser user = new AppUser();
        user.setEmail("student@osu.cz");
        user.setPassword("heslo");

        when(userRepository.findByEmailIgnoreCase("student@osu.cz")).thenReturn(user);

        assertThrows(RuntimeException.class, () -> userService.login("student@osu.cz", "wrongpassword"));
    }

    @Test
    void login_userDoesNotExist_throwsException() {
        when(userRepository.findByEmailIgnoreCase("unknown@osu.cz")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> userService.login("unknown@osu.cz", "heslo"));
    }
}