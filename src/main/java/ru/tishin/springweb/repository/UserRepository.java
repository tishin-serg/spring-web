package ru.tishin.springweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tishin.springweb.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsUserByUsername(String username);
    boolean existsUserByEmail(String email);

    @Query(value = "SELECT id FROM users WHERE username = :username",nativeQuery = true)
    Optional<Long> findIdByUsername(String username);
}
