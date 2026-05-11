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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private AppUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    // ---------- login ----------

    @Test
    void login_validCredentials_returnsUserToken() {
        // Arrange
        UUID userId = UUID.randomUUID();
        AppUser user = new AppUser();
        user.setUserId(userId);
        user.setName("Test User");
        user.setEmail("student@osu.cz");
        user.setPassword("heslo");
        user.setRole(Role.MEMBER);

        when(userRepository.findByEmailIgnoreCase("student@osu.cz")).thenReturn(user);

        // Act
        UserToken token = userService.login("student@osu.cz", "heslo");

        // Assert
        assertNotNull(token);
        assertEquals(userId, token.getUserId()); // Matches the fix: real UUID is now returned
        assertEquals("student@osu.cz", token.getEmail());
        assertEquals("Test User", token.getName());
        assertEquals(Role.MEMBER, token.getRole());
    }

    @Test
    void login_wrongPassword_throwsException() {
        // Arrange
        AppUser user = new AppUser();
        user.setEmail("student@osu.cz");
        user.setPassword("heslo");

        when(userRepository.findByEmailIgnoreCase("student@osu.cz")).thenReturn(user);

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.login("student@osu.cz", "wrongpassword"));

        assertEquals("Wrong password!", ex.getMessage()); // Matches logic in UserService.java
    }

    @Test
    void login_userDoesNotExist_throwsException() {
        // Arrange
        when(userRepository.findByEmailIgnoreCase("unknown@osu.cz")).thenReturn(null);

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.login("unknown@osu.cz", "heslo"));

        assertEquals("User does not exist!", ex.getMessage()); // Matches logic in UserService.java
    }

    // ---------- register ----------

    @Test
    void register_success() {
        // Arrange
        String email = "newuser@osu.cz";
        when(userRepository.existsByEmailIgnoreCase(email)).thenReturn(false);

        // Act
        UserToken token = userService.register("New User", email, "password");

        // Assert
        assertNotNull(token);
        assertEquals("New User", token.getName());
        assertEquals(email, token.getEmail());
        verify(userRepository, times(1)).save(any(AppUser.class));
    }

    @Test
    void register_duplicateEmail_throwsException() {
        // Arrange
        String email = "existing@osu.cz";
        when(userRepository.existsByEmailIgnoreCase(email)).thenReturn(true);

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.register("Name", email, "pass"));

        assertEquals("User with this email already exists.", ex.getMessage());
    }
}