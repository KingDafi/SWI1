package cz.osu.swi1_sm.model.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Borrowing> borrowings = new ArrayList<>();

    public AppUser() {}

    public AppUser(UUID userId, String username, String password, Role role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UUID getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public List<Borrowing> getBorrowings() { return borrowings; }

    public void setUserId(UUID userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(Role role) { this.role = role; }
    public void setBorrowings(List<Borrowing> borrowings) { this.borrowings = borrowings; }
}