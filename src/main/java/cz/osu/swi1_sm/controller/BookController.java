package cz.osu.swi1_sm.controller;

import cz.osu.swi1_sm.model.entity.Book;
import cz.osu.swi1_sm.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String query) {
        return bookService.getBooks(query);
    }

    @PostMapping("/{id}/borrow")
    public void borrowBook(@PathVariable String id, @RequestParam UUID userId) {
        bookService.borrowBook(id, userId);
    }

    @PostMapping("/{id}/return")
    public void returnBook(@PathVariable String id) {
        bookService.returnBook(id);
    }
}