package cz.osu.swi1_sm.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID borrowingId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @JsonProperty("borrowedDate")
    @Column(nullable = false)
    private LocalDate borrowedAt;

    @Column(nullable = true)
    private LocalDate returnedAt;

    @Column(nullable = false)
    private LocalDate dueDate;

    public Borrowing() {}

    public Borrowing(UUID borrowingId, AppUser appUser, Book book,
                     LocalDate borrowedAt, LocalDate returnedAt, LocalDate dueDate) {
        this.borrowingId = borrowingId;
        this.appUser = appUser;
        this.book = book;
        this.borrowedAt = borrowedAt;
        this.returnedAt = returnedAt;
        this.dueDate = dueDate;
    }

    public UUID getBorrowingId() { return borrowingId; }
    public AppUser getAppUser() { return appUser; }
    public Book getBook() { return book; }
    public LocalDate getBorrowedAt() { return borrowedAt; }
    public LocalDate getReturnedAt() { return returnedAt; }
    public LocalDate getDueDate() { return dueDate; }

    public void setBorrowingId(UUID borrowingId) { this.borrowingId = borrowingId; }
    public void setAppUser(AppUser appUser) { this.appUser = appUser; }
    public void setBook(Book book) { this.book = book; }
    public void setBorrowedAt(LocalDate borrowedAt) { this.borrowedAt = borrowedAt; }
    public void setReturnedAt(LocalDate returnedAt) { this.returnedAt = returnedAt; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}