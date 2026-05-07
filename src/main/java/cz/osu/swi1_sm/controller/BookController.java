package cz.osu.swi1_sm.controller;

import cz.osu.swi1_sm.model.dto.BorrowRequest;
import cz.osu.swi1_sm.model.entity.Book;
import cz.osu.swi1_sm.service.BookService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> borrowBook(
            @PathVariable String id,
            @RequestBody BorrowRequest request
    ) {
        bookService.borrowBook(id, request.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Void> returnBook(
            @PathVariable String id,
            @RequestBody BorrowRequest request
    ) {
        bookService.returnBook(id, request.getUserId());
        return ResponseEntity.noContent().build();
    }
}