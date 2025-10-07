package com.store.domain.Infraestructure.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.domain.Domain.Models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}