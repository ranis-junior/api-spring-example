package com.teste.DesafioBento.service;

import com.teste.DesafioBento.error.RepeatedDataException;
import com.teste.DesafioBento.model.Product;
import com.teste.DesafioBento.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements AbstractService<Product> {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<List<Product>> findAll() {
        return Optional.ofNullable(productRepository.findAll());
    }

    @Override
    public Product insertOrUpdateEntity(Product entity) {
        if (entity.getId() == null) {
            Optional<List<Product>> products = productRepository.findByStoreId(entity.getStore().getId());
            if (products.isPresent() && products.get().stream().filter(
                    p -> p.getName().equals(entity.getName())).count() > 0)
                throw new RepeatedDataException("Este produto já se encontra cadastrado.");
        }
        return productRepository.save(entity);
    }

    @Override
    public void deleteEntity(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<List<Product>> findAllByName(String name) {
        return Optional.ofNullable(productRepository.findAllByNameContainingIgnoreCase(name));
    }
}
