package cz.osu.swi1_sm.model.dto;

import cz.osu.swi1_sm.model.entity.Role;

public class UserToken {
    private String name;
    private Role role;

    public UserToken(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public String getName() { return name; }
    public Role getRole() { return role; }

    public void setName(String name) { this.name = name; }
    public void setRole(Role role) { this.role = role; }
}