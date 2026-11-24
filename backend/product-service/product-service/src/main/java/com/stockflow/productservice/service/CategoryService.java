package com.stockflow.productservice.service;

import com.stockflow.productservice.dto.CategoryRequest;
import com.stockflow.productservice.entity.Category;
import com.stockflow.productservice.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category create(CategoryRequest req) {
        String name = req.name().trim();
        if (repository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "La catégorie '" + name + "' existe déjà");
        }
        return repository.save(new Category(name));
    }
}