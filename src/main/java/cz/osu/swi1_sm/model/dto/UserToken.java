package cz.osu.swi1_sm.model.dto;

import cz.osu.swi1_sm.model.entity.Role;

import java.util.UUID;

public class UserToken {
    private String name;
    private String email;
    private Role role;
    private UUID userId;

    public UserToken(UUID userId, String name, String email, Role role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(Role role) { this.role = role; }
}