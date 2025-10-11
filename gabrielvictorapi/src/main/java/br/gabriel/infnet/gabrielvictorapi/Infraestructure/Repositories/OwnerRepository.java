package br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Owner;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.User;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    @Query("SELECT DISTINCT o FROM Owner o JOIN FETCH o.products WHERE o.user = :user AND o.isActive = true")
    List<Owner> findActiveOwnersWithProducts(@Param("user") User user);

}

