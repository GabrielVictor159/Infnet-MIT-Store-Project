package br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByIdIn(List<Integer> ids);
    @Query("""
      SELECT DISTINCT p FROM Product p
      LEFT JOIN FETCH p.owners o
      LEFT JOIN FETCH o.user
      WHERE o.id in :ids
    """)
   List<Product> findByInIdWithDeepAssociations(@Param("ids") List<Integer> ids);
}