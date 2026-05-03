package cz.osu.swi1_sm.model.repository;

import cz.osu.swi1_sm.model.entity.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, UUID> {
    boolean existsByEmailIgnoreCase(String username);
    AppUser findByEmailIgnoreCase(String username);
}
