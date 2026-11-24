package com.stockflow.productservice.dto;

import jakarta.validation.constraints.*;

public record ProductRequest(
        @NotBlank @Size(max = 50) String sku,
        @NotBlank @Size(max = 200) String name,
        @Size(max = 1000) String description,
        Long categoryId,
        @Min(0) int minStock
) { }