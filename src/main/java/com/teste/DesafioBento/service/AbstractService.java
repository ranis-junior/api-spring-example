package com.teste.DesafioBento.service;

import java.util.List;
import java.util.Optional;

public interface AbstractService<R> {
    Optional<R> findById(Long id);

    Optional<List<R>> findAll();

    R insertOrUpdateEntity(R entity);

    void deleteEntity(Long id);
}
