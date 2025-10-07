package com.store.domain.Infraestructure.Repositories;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.domain.Domain.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email);
    Optional<User> findByCreateId(UUID id);
}
