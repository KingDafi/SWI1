package cz.osu.swi1_sm.service;

import cz.osu.swi1_sm.model.entity.AppUser;
import cz.osu.swi1_sm.model.entity.Book;
import cz.osu.swi1_sm.model.entity.Borrowing;
import cz.osu.swi1_sm.model.repository.AppUserRepository;
import cz.osu.swi1_sm.model.repository.BookRepository;
import cz.osu.swi1_sm.model.repository.BorrowingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowingRepository borrowingRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private BookService bookService;

    // ---------- getBooks ----------

    @Test
    void getBooks_nullQuery_returnsAllBooks() {
        Book book = createBook();
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<Book> result = bookService.getBooks(null);

        assertEquals(1, result.size());
        verify(bookRepository).findAll();
    }

    // ---------- borrowBook ----------

    @Test
    void borrowBook_success() {
        Book book = createBook();
        AppUser user = new AppUser();
        UUID userId = UUID.randomUUID();

        // Mocking the sequence in BookService.java
        when(bookRepository.findById(book.getBookId())).thenReturn(Optional.of(book));
        // Check alreadyBorrowed logic
        when(borrowingRepository.existsByAppUser_UserIdAndBook_BookIdAndReturnedAtIsNull(userId, book.getBookId()))
                .thenReturn(false);
        when(appUserRepository.findById(userId)).thenReturn(Optional.of(user));

        bookService.borrowBook(book.getBookId().toString(), userId);

        assertEquals(2, book.getAvailableQuantity()); // 3 - 1
        verify(bookRepository).save(book);
        verify(borrowingRepository).save(any(Borrowing.class));
    }

    @Test
    void borrowBook_alreadyBorrowed_throwsException() {
        UUID bookId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Book book = createBook();
        book.setBookId(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(borrowingRepository.existsByAppUser_UserIdAndBook_BookIdAndReturnedAtIsNull(userId, bookId))
                .thenReturn(true);

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> bookService.borrowBook(bookId.toString(), userId));

        assertEquals("User already borrowed this book", ex.getMessage());
    }

    @Test
    void borrowBook_noAvailableCopies_throwsException() {
        Book book = createBook();
        book.setAvailableQuantity(0);
        UUID userId = UUID.randomUUID();

        when(bookRepository.findById(book.getBookId())).thenReturn(Optional.of(book));
        when(borrowingRepository.existsByAppUser_UserIdAndBook_BookIdAndReturnedAtIsNull(userId, book.getBookId()))
                .thenReturn(false);

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> bookService.borrowBook(book.getBookId().toString(), userId));

        assertEquals("No copies available", ex.getMessage());
    }

    // ---------- returnBook ----------

    @Test
    void returnBook_success() {
        Book book = createBook();
        UUID userId = UUID.randomUUID();
        Borrowing borrowing = new Borrowing();
        borrowing.setBook(book);
        borrowing.setReturnedAt(null);

        when(bookRepository.findById(book.getBookId())).thenReturn(Optional.of(book));
        // BookService.java uses findByAppUser_UserId(userId).stream() for return
        when(borrowingRepository.findByAppUser_UserId(userId)).thenReturn(List.of(borrowing));

        bookService.returnBook(book.getBookId().toString(), userId);

        assertEquals(4, book.getAvailableQuantity()); // 3 + 1
        assertNotNull(borrowing.getReturnedAt());
        verify(borrowingRepository).save(borrowing);
        verify(bookRepository).save(book);
    }

    @Test
    void returnBook_noActiveBorrowing_throwsException() {
        Book book = createBook();
        UUID userId = UUID.randomUUID();

        when(bookRepository.findById(book.getBookId())).thenReturn(Optional.of(book));
        when(borrowingRepository.findByAppUser_UserId(userId)).thenReturn(List.of());

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> bookService.returnBook(book.getBookId().toString(), userId));

        assertEquals("This user does not have this book borrowed", ex.getMessage());
    }

    private Book createBook() {
        Book book = new Book();
        book.setBookId(UUID.randomUUID());
        book.setTitle("Clean Code");
        book.setAuthor("Robert Martin");
        book.setCategory("Programming");
        book.setQuantity(5);
        book.setAvailableQuantity(3);
        book.setPrice(BigDecimal.valueOf(500));
        return book;
    }
}