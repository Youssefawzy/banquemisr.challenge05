package com.banquemisr.challenge05.repository;

import com.banquemisr.challenge05.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {


    Optional<Role> findByName(String name);
}
