package com.FreteGen.FreteGen.repository;

import com.FreteGen.FreteGen.user.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Products, UUID> {
    Optional<Products> findByName(String name);
}
