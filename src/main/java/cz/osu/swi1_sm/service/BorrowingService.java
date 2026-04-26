package cz.osu.swi1_sm.service;

import cz.osu.swi1_sm.model.entity.Borrowing;
import cz.osu.swi1_sm.model.repository.BorrowingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BorrowingService {
    private final BorrowingRepository borrowingRepository;

    public BorrowingService(BorrowingRepository borrowingRepository) {
        this.borrowingRepository = borrowingRepository;
    }

    public List<Borrowing> getBorrowingsForUser(UUID userId) {
        return borrowingRepository.findByAppUser_UserId(userId);
    }
}