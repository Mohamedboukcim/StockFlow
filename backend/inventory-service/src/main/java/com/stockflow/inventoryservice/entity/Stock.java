package com.stockflow.inventoryservice.entity;

import com.stockflow.inventoryservice.exception.InsufficientStockException;
import jakarta.persistence.*;

@Entity
@Table(name = "stock",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_stock_product_warehouse",
                columnNames = {"product_id", "warehouse_id"}))
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "warehouse_id", nullable = false)
    private Long warehouseId;

    @Column(nullable = false)
    private int quantity;

    @Version
    private Long version;

    protected Stock() { }

    public Stock(Long productId, Long warehouseId, int quantity) {
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
    }

    public void increase(int amount) {
        this.quantity += amount;
    }

    public void decrease(int amount) {
        if (amount > this.quantity) {
            throw new InsufficientStockException(
                    productId, warehouseId, this.quantity, amount);
        }
        this.quantity -= amount;
    }

    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public Long getWarehouseId() { return warehouseId; }
    public int getQuantity() { return quantity; }
    public Long getVersion() { return version; }
}