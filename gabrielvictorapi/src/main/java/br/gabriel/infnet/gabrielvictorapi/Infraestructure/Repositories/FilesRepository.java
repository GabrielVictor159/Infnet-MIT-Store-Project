package br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Files;


@Repository
public interface FilesRepository extends JpaRepository<Files, UUID> {
    
}
