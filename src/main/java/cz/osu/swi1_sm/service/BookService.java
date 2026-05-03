package cz.osu.swi1_sm.service;

import cz.osu.swi1_sm.model.entity.AppUser;
import cz.osu.swi1_sm.model.entity.Book;
import cz.osu.swi1_sm.model.entity.Borrowing;
import cz.osu.swi1_sm.model.repository.AppUserRepository;
import cz.osu.swi1_sm.model.repository.BookRepository;
import cz.osu.swi1_sm.model.repository.BorrowingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BorrowingRepository borrowingRepository;
    private final AppUserRepository appUserRepository;

    public BookService(BookRepository bookRepository, BorrowingRepository borrowingRepository, AppUserRepository appUserRepository) {
        this.bookRepository = bookRepository;
        this.borrowingRepository = borrowingRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<Book> getBooks(String query) {
        if (query == null || query.isBlank()) {
            return (List<Book>) bookRepository.findAll();
        }
        List<Book> byTitle = bookRepository.findByTitleContainingIgnoreCase(query);
        List<Book> byAuthor = bookRepository.findByAuthorContainingIgnoreCase(query);
        List<Book> byCategory = bookRepository.findByCategoryContainingIgnoreCase(query);
        return java.util.stream.Stream.of(byTitle, byAuthor, byCategory)
                .flatMap(List::stream)
                .distinct()
                .toList();
    }

    public void borrowBook(String id, UUID userId) {
        Book book = bookRepository.findById(UUID.fromString(id)).orElseThrow();
        if (book.getAvailableQuantity() <= 0) {
            throw new IllegalStateException("No copies available");
        }
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);

        AppUser user = appUserRepository.findById(userId).orElseThrow();
        Borrowing borrowing = new Borrowing();
        borrowing.setAppUser(user);
        borrowing.setBook(book);
        borrowing.setBorrowedAt(LocalDate.now());
        borrowing.setDueDate(LocalDate.now().plusDays(30));
        borrowingRepository.save(borrowing);
    }

    @Transactional
    public void returnBook(String id) {
        UUID bookId = UUID.fromString(id);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        Borrowing borrowing = borrowingRepository.findByBook_BookId(bookId).stream()
                .filter(b -> b.getReturnedAt() == null)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("This book is not currently borrowed"));

        borrowing.setReturnedAt(LocalDate.now());
        borrowingRepository.save(borrowing);

        if (book.getAvailableQuantity() >= book.getQuantity()) {
            throw new IllegalStateException("All copies already returned");
        }

        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        bookRepository.save(book);
    }
}