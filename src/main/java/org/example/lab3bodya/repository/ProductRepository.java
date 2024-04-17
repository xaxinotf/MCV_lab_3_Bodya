package org.example.lab3bodya.repository;

import org.example.lab3bodya.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
