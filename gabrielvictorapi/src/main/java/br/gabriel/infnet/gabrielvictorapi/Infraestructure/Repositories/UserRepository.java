package br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gabriel.infnet.gabrielvictorapi.Domain.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email);
    Optional<User> findByCreateId(UUID id);
    Optional<User> findByAlterPasswordId(UUID id);
}
