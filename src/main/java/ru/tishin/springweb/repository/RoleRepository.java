package ru.tishin.springweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tishin.springweb.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String nameRole);
}
