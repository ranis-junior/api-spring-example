package com.teste.DesafioBento.api;

import com.teste.DesafioBento.error.ApiError;
import com.teste.DesafioBento.model.Store;
import com.teste.DesafioBento.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/store")
@CrossOrigin("*")
public class StoreController {
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public ResponseEntity listAll() {
        return ResponseEntity.ok(storeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getStoreById(@PathVariable("id") Long id) {
        return storeService.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity insertEntity(@RequestBody @Valid @NonNull Store store, BindingResult result) {
        if (result.hasErrors()) {
            ApiError errors = new ApiError(result.getAllErrors(), HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(errors);
        }
        return insertOrUpdateEntity(store, result);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEntity(@PathVariable("id") Long id, @RequestBody @Valid @NonNull Store store, BindingResult result) {
        if (result.hasErrors()) {
            ApiError errors = new ApiError(result.getAllErrors(), HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(errors);
        }
        return storeService.findById(id).map(record -> {
            record.setName(store.getName());
            record.setAddress(store.getAddress());
            record.setTelephone(store.getTelephone());
            return insertOrUpdateEntity(record, result);
        }).orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEntity(@PathVariable("id") Long id) {
        // Poderia ser feito usando um findbyId -> map -> orElse, porém usa menos recursos seguindo o padrão EAFP
        try {
            storeService.deleteEntity(id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/name")
    public ResponseEntity findAllByName(@RequestBody Store store) {
        return storeService.findAllByName(store.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    private ResponseEntity insertOrUpdateEntity(@NonNull @RequestBody @Valid Store store, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        storeService.insertOrUpdateEntity(store);
        return ResponseEntity.ok(store);
    }

}
