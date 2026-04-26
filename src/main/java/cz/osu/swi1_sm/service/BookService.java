package cz.osu.swi1_sm.service;

import cz.osu.swi1_sm.model.entity.Book;
import cz.osu.swi1_sm.model.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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

    public void borrowBook(String id) {
        Book book = bookRepository.findById(UUID.fromString(id)).orElseThrow();
        if (book.getAvailableQuantity() <= 0) {
            throw new IllegalStateException("No copies available");
        }
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);
    }

    public void returnBook(String id) {
        Book book = bookRepository.findById(UUID.fromString(id)).orElseThrow();
        if (book.getAvailableQuantity() >= book.getQuantity()) {
            throw new IllegalStateException("All copies already returned");
        }
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        bookRepository.save(book);
    }
}