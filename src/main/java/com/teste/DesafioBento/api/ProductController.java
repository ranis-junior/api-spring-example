package com.teste.DesafioBento.api;

import com.teste.DesafioBento.error.ApiError;
import com.teste.DesafioBento.model.Product;
import com.teste.DesafioBento.service.ProductService;
import com.teste.DesafioBento.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin("*")
@Scope()
public class ProductController {
    private final ProductService productService;
    private final StoreService storeService;

    @Autowired
    public ProductController(ProductService productService, StoreService storeService) {
        this.productService = productService;
        this.storeService = storeService;
    }

    @GetMapping
    public ResponseEntity listAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable("id") Long id) {
        return productService.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity insertEntity(@RequestBody @Valid @NonNull Product product, BindingResult result) {
        if (result.hasErrors()) {
            ApiError errors = new ApiError(result.getAllErrors(), HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(errors);
        }
        return storeService.findById(product.getStore().getId()).map(record -> {
            product.setStore(record);
            return insertOrUpdateEntity(product, result);
        }).orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEntity(@PathVariable("id") Long id, @RequestBody @Valid @NonNull Product product, BindingResult result) {
        if (result.hasErrors()) {
            ApiError errors = new ApiError(result.getAllErrors(), HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(errors);
        }
        return productService.findById(id).map(record -> {
            record.setName(product.getName());
            record.setBarCode(product.getBarCode());
            record.setCategory(product.getCategory());
            record.setPrice(product.getPrice());
            return insertOrUpdateEntity(record, result);
        }).orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEntity(@PathVariable("id") Long id) {
        try {
            productService.deleteEntity(id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/name")
    public ResponseEntity findAllByName(@RequestBody Product product) {
        return productService.findAllByName(product.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    private ResponseEntity insertOrUpdateEntity(@NonNull @RequestBody @Valid Product product, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        productService.insertOrUpdateEntity(product);
        return ResponseEntity.ok(product);
    }

}
