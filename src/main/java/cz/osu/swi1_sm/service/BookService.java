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
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrCategoryContainingIgnoreCase(
                query, query, query
        );
    }

    @Transactional
    public void borrowBook(String id, UUID userId) {

        UUID bookId = UUID.fromString(id);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        boolean alreadyBorrowed =
                borrowingRepository
                        .existsByAppUser_UserIdAndBook_BookIdAndReturnedAtIsNull(userId, bookId);

        if (alreadyBorrowed) {
            throw new IllegalStateException("User already borrowed this book");
        }

        if (book.getAvailableQuantity() <= 0) {
            throw new IllegalStateException("No copies available");
        }

        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);

        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Borrowing borrowing = new Borrowing();
        borrowing.setAppUser(user);
        borrowing.setBook(book);
        borrowing.setBorrowedAt(LocalDate.now());
        borrowing.setDueDate(LocalDate.now().plusDays(30));

        borrowingRepository.save(borrowing);
    }

    @Transactional
    public void returnBook(String id, UUID userId) { // Added userId parameter
        UUID bookId = UUID.fromString(id);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        // Find the specific borrowing for THIS user that hasn't been returned yet
        Borrowing borrowing = borrowingRepository.findByBook_BookId(bookId).stream()
                .filter(b -> b.getReturnedAt() == null && b.getAppUser().getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("This user does not have an active borrowing for this book"));

        borrowing.setReturnedAt(LocalDate.now());
        borrowingRepository.save(borrowing);

        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        bookRepository.save(book);
    }
}