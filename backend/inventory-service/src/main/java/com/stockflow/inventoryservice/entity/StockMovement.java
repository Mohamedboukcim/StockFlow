package com.stockflow.inventoryservice.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "stock_movements")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MovementType type;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "source_warehouse_id")
    private Long sourceWarehouseId;

    @Column(name = "destination_warehouse_id")
    private Long destinationWarehouseId;

    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected StockMovement() { }

    public StockMovement(Long productId, MovementType type, int quantity,
                         Long sourceWarehouseId, Long destinationWarehouseId,
                         String createdBy) {
        this.productId = productId;
        this.type = type;
        this.quantity = quantity;
        this.sourceWarehouseId = sourceWarehouseId;
        this.destinationWarehouseId = destinationWarehouseId;
        this.createdBy = createdBy;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public MovementType getType() { return type; }
    public int getQuantity() { return quantity; }
    public Long getSourceWarehouseId() { return sourceWarehouseId; }
    public Long getDestinationWarehouseId() { return destinationWarehouseId; }
    public String getCreatedBy() { return createdBy; }
    public Instant getCreatedAt() { return createdAt; }
}