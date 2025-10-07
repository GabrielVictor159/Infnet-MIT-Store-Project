package com.store.domain.Infraestructure.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.domain.Domain.Models.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
}
