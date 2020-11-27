package com.teste.DesafioBento.repository;

import com.teste.DesafioBento.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Transactional(readOnly = true)
    List<Store> findAllByNameContainingIgnoreCase(String name);
}
