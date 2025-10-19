package br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

}