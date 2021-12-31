package ru.tishin.springweb.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tishin.springweb.entities.Role;
import ru.tishin.springweb.entities.User;
import ru.tishin.springweb.repository.RoleRepository;

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
