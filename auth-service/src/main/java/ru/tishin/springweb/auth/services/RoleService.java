package ru.tishin.springweb.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tishin.springweb.auth.entities.Role;
import ru.tishin.springweb.auth.entities.User;
import ru.tishin.springweb.auth.repository.RoleRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final String DEFAULT_ROLE = "ROLE_USER";
    private final RoleRepository roleRepository;

    public void setDefaultRole(User user) {
        Collection<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findRoleByName(DEFAULT_ROLE));
        user.setRoles(roles);
    }

}
