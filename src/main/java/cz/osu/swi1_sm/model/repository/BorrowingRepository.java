package cz.osu.swi1_sm.model.repository;

import cz.osu.swi1_sm.model.entity.Borrowing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BorrowingRepository extends CrudRepository<Borrowing, UUID> {
    List<Borrowing> findByAppUser_UserId(UUID userId);
    List<Borrowing> findByBook_BookId(UUID bookId);
}