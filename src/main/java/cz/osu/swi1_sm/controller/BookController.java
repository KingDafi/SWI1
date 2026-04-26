package cz.osu.swi1_sm.controller;

import cz.osu.swi1_sm.model.entity.Book;
import cz.osu.swi1_sm.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void borrowBook(@PathVariable String id) {
        bookService.borrowBook(id);
    }

    @PostMapping("/{id}/return")
    public void returnBook(@PathVariable String id) {
        bookService.returnBook(id);
    }
}