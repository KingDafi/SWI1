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

        when(bookRepository.findById(book.getBookId()))
                .thenReturn(Optional.of(book));
        when(appUserRepository.findById(userId))
                .thenReturn(Optional.of(user));

        bookService.borrowBook(book.getBookId().toString(), userId);

        assertEquals(2, book.getAvailableQuantity());
        verify(bookRepository).save(book);
        verify(borrowingRepository).save(any(Borrowing.class));
    }

    @Test
    void borrowBook_noAvailableCopies_throwsException() {
        Book book = createBook();
        book.setAvailableQuantity(0);

        when(bookRepository.findById(book.getBookId()))
                .thenReturn(Optional.of(book));

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> bookService.borrowBook(book.getBookId().toString(), UUID.randomUUID())
        );

        assertEquals("No copies available", ex.getMessage());
        verify(borrowingRepository, never()).save(any());
    }

    // ---------- returnBook ----------

    @Test
    void returnBook_success() {
        Book book = createBook();

        when(bookRepository.findById(book.getBookId()))
                .thenReturn(Optional.of(book));

        bookService.returnBook(book.getBookId().toString());

        assertEquals(4, book.getAvailableQuantity());
        verify(bookRepository).save(book);
    }

    @Test
    void returnBook_allCopiesReturned_throwsException() {
        Book book = createBook();
        book.setAvailableQuantity(book.getQuantity());

        when(bookRepository.findById(book.getBookId()))
                .thenReturn(Optional.of(book));

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> bookService.returnBook(book.getBookId().toString())
        );

        assertEquals("All copies already returned", ex.getMessage());
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