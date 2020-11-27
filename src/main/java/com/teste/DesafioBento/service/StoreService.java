package com.teste.DesafioBento.service;

import com.teste.DesafioBento.model.Store;
import com.teste.DesafioBento.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService implements AbstractService<Store> {
    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public Optional<Store> findById(Long id) {
        return storeRepository.findById(id);
    }

    @Override
    public Optional<List<Store>> findAll() {
        return Optional.ofNullable(storeRepository.findAll());
    }

    public Optional<List<Store>> findAllByName(String name) {
        return Optional.ofNullable(storeRepository.findAllByNameContainingIgnoreCase(name));
    }

    @Override
    public Store insertOrUpdateEntity(Store entity) {
        return storeRepository.save(entity);
    }

    @Override
    public void deleteEntity(Long id) {
        storeRepository.deleteById(id);
    }
}
