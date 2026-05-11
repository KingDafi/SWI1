package cz.osu.swi1_sm.service;

import cz.osu.swi1_sm.model.entity.AppUser;
import cz.osu.swi1_sm.model.entity.Book;
import cz.osu.swi1_sm.model.entity.Borrowing;
import cz.osu.swi1_sm.model.repository.AppUserRepository;
import cz.osu.swi1_sm.model.repository.BookRepository;
import cz.osu.swi1_sm.model.repository.BorrowingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final BookRepository bookRepository;
    private final AppUserRepository userRepository;

    public BorrowingService(BorrowingRepository borrowingRepository, BookRepository bookRepository, AppUserRepository userRepository) {
        this.borrowingRepository = borrowingRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<Borrowing> getBorrowingsForUser(UUID userId) {
        return borrowingRepository.findByAppUser_UserId(userId);
    }

    @Transactional
    public Borrowing borrowBook(UUID bookId, UUID userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Kniha nebyla nalezena."));

        boolean alreadyBorrowed = borrowingRepository.existsByAppUser_UserIdAndBook_BookIdAndReturnedAtIsNull(userId, bookId);
        if (alreadyBorrowed) {
            throw new RuntimeException("Tuto knihu již máte vypůjčenou.");
        }

        if (book.getAvailableQuantity() <= 0) {
            throw new RuntimeException("Kniha je momentálně rozebraná.");
        }

        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);

        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Uživatel nenalezen."));

        Borrowing borrowing = new Borrowing();
        borrowing.setBook(book);
        borrowing.setAppUser(user);

        borrowing.setBorrowedAt(LocalDate.now());
        borrowing.setDueDate(LocalDate.now().plusDays(30));

        return borrowingRepository.save(borrowing);
    }

    @Transactional
    public void returnBook(UUID bookId, UUID userId) {
        Borrowing borrowing = borrowingRepository.findByAppUser_UserIdAndBook_BookIdAndReturnedAtIsNull(userId, bookId)
                .orElseThrow(() -> new RuntimeException("Aktivní výpůjčka nenalezena."));

        borrowing.setReturnedAt(LocalDate.now());
        borrowingRepository.save(borrowing);

        Book book = borrowing.getBook();
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        bookRepository.save(book);
    }
}