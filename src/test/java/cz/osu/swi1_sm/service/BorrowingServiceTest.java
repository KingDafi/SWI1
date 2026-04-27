package cz.osu.swi1_sm.service;

import cz.osu.swi1_sm.model.entity.Borrowing;
import cz.osu.swi1_sm.model.repository.BorrowingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BorrowingServiceTest {

    @Mock
    private BorrowingRepository borrowingRepository;

    @InjectMocks
    private BorrowingService borrowingService;

    @Test
    void getBorrowingsForUser_returnsBorrowings() {
        UUID userId = UUID.randomUUID();

        Borrowing borrowing1 = new Borrowing();
        Borrowing borrowing2 = new Borrowing();

        when(borrowingRepository.findByAppUser_UserId(userId))
                .thenReturn(List.of(borrowing1, borrowing2));

        List<Borrowing> result = borrowingService.getBorrowingsForUser(userId);

        assertEquals(2, result.size());
        verify(borrowingRepository).findByAppUser_UserId(userId);
    }

    @Test
    void getBorrowingsForUser_noBorrowings_returnsEmptyList() {
        UUID userId = UUID.randomUUID();

        when(borrowingRepository.findByAppUser_UserId(userId))
                .thenReturn(List.of());

        List<Borrowing> result = borrowingService.getBorrowingsForUser(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}