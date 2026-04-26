package cz.osu.swi1_sm.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID bookId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = true)
    private String coverUrl;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int availableQuantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = true)
    private BigDecimal originalPrice;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Borrowing> borrowings = new ArrayList<>();

    public Book() {}

    public Book(UUID bookId, String title, String author, String coverUrl,
                String category, int quantity, int availableQuantity,
                BigDecimal price, BigDecimal originalPrice) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.coverUrl = coverUrl;
        this.category = category;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
        this.price = price;
        this.originalPrice = originalPrice;
    }

    public boolean isAvailable() {
        return this.availableQuantity > 0;
    }

    public UUID getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCoverUrl() { return coverUrl; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public int getAvailableQuantity() { return availableQuantity; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getOriginalPrice() { return originalPrice; }
    public List<Borrowing> getBorrowings() { return borrowings; }

    public void setBookId(UUID bookId) { this.bookId = bookId; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }
    public void setCategory(String category) { this.category = category; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setOriginalPrice(BigDecimal originalPrice) { this.originalPrice = originalPrice; }
    public void setBorrowings(List<Borrowing> borrowings) { this.borrowings = borrowings; }
}