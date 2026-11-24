package com.stockflow.productservice.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "min_stock", nullable = false)
    private int minStock;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected Product() { }

    public Product(String sku, String name, String description,
                   Category category, int minStock) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.category = category;
        this.minStock = minStock;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    // --- getters ---
    public Long getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public int getMinStock() { return minStock; }
    public Instant getCreatedAt() { return createdAt; }

    // --- modification contrôlée ---
    public void update(String name, String description,
                       Category category, int minStock) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.minStock = minStock;
    }
}