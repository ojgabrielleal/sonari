package com.sonari.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sonari.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUuid(UUID uuid);

    Optional<User> findByUsername(String username);
}