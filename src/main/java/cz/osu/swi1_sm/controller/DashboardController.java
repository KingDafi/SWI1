package cz.osu.swi1_sm.controller;

import cz.osu.swi1_sm.model.entity.Borrowing;
import cz.osu.swi1_sm.service.BorrowingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/borrowings")
public class DashboardController {
    private final BorrowingService borrowingService;

    public DashboardController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @GetMapping("/{userId}")
    public List<Borrowing> getBorrowings(@PathVariable UUID userId) {
        return borrowingService.getBorrowingsForUser(userId);
    }
}