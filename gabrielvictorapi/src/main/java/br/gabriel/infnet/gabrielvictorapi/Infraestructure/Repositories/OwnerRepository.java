package br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
}
