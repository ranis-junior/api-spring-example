package com.teste.DesafioBento.repository;

import com.teste.DesafioBento.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Transactional(readOnly = true)
    List<Product> findAllByNameContainingIgnoreCase(String name);

    @Transactional(readOnly = true)
    Optional<List<Product>> findByStoreId(Long id);
}
