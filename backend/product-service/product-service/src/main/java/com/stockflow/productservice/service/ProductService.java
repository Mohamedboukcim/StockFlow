package com.stockflow.productservice.service;

import com.stockflow.productservice.dto.ProductRequest;
import com.stockflow.productservice.dto.ProductResponse;
import com.stockflow.productservice.entity.Category;
import com.stockflow.productservice.entity.Product;
import com.stockflow.productservice.exception.DuplicateSkuException;
import com.stockflow.productservice.exception.ResourceNotFoundException;
import com.stockflow.productservice.repository.CategoryRepository;
import com.stockflow.productservice.repository.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> search(String query, Pageable pageable) {
        Page<Product> page = (query == null || query.isBlank())
                ? productRepository.findAll(pageable)
                : productRepository
                .findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(
                        query, query, pageable);
        return page.map(ProductResponse::from);
    }

    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        return ProductResponse.from(findOrThrow(id));
    }

    @Transactional
    public ProductResponse create(ProductRequest req) {
        String sku = req.sku().trim().toUpperCase();
        if (productRepository.existsBySku(sku)) {
            throw new DuplicateSkuException(sku);
        }
        Category category = resolveCategory(req.categoryId());
        Product product = new Product(sku, req.name().trim(),
                req.description(), category, req.minStock());
        try {
            return ProductResponse.from(productRepository.saveAndFlush(product));
        } catch (DataIntegrityViolationException e) {
            // filet de sécurité : deux requêtes simultanées avec le même SKU
            throw new DuplicateSkuException(sku);
        }
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest req) {
        Product product = findOrThrow(id);
        Category category = resolveCategory(req.categoryId());
        product.update(req.name().trim(), req.description(), category, req.minStock());
        return ProductResponse.from(product);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.delete(findOrThrow(id));
    }

    // ---------- méthodes privées ----------

    private Product findOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit", id));
    }

    private Category resolveCategory(Long categoryId) {
        if (categoryId == null) return null;
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie", categoryId));
    }
}