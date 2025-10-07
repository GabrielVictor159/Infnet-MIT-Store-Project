package com.store.domain.Infraestructure.Repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.store.domain.Domain.Models.Files;


@Repository
public interface FilesRepository extends JpaRepository<Files, UUID> {
    
}
