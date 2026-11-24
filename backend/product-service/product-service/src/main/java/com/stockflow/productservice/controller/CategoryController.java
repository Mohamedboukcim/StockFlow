package com.stockflow.productservice.controller;

import com.stockflow.productservice.dto.CategoryRequest;
import com.stockflow.productservice.entity.Category;
import com.stockflow.productservice.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    public record CategoryResponse(Long id, String name) {
        static CategoryResponse from(Category c) {
            return new CategoryResponse(c.getId(), c.getName());
        }
    }

    @GetMapping
    public List<CategoryResponse> list() {
        return service.findAll().stream().map(CategoryResponse::from).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@Valid @RequestBody CategoryRequest request) {
        return CategoryResponse.from(service.create(request));
    }
}