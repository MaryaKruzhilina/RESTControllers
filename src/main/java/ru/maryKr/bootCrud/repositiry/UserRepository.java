package ru.maryKr.bootCrud.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maryKr.bootCrud.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
   // User findByUsername(String username);
}
