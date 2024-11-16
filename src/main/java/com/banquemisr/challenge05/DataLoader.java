package com.banquemisr.challenge05;

import com.banquemisr.challenge05.repository.RoleRepository;
import com.banquemisr.challenge05.repository.UserRepository;
import com.banquemisr.challenge05.model.Role;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EntityManager entityManager;
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {

            Optional<Role> adminRoleOptional = roleRepository.findByName("ROLE_ADMIN");
            Role adminRole = adminRoleOptional.orElseGet(() -> {
                Role newAdminRole = new Role("ROLE_ADMIN");
                return roleRepository.save(newAdminRole);
            });

            Optional<Role> userRoleOptional = roleRepository.findByName("ROLE_USER");
            Role userRole = userRoleOptional.orElseGet(() -> {
                Role newUserRole = new Role("ROLE_USER");
                return roleRepository.save(newUserRole);
            });

        }
    }
}
