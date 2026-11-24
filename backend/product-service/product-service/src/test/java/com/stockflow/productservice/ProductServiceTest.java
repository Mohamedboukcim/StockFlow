package com.stockflow.productservice;

import com.stockflow.productservice.dto.ProductRequest;
import com.stockflow.productservice.exception.DuplicateSkuException;
import com.stockflow.productservice.repository.CategoryRepository;
import com.stockflow.productservice.repository.ProductRepository;
import com.stockflow.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock ProductRepository productRepository;
    @Mock CategoryRepository categoryRepository;
    @InjectMocks
    ProductService service;

    @Test
    void create_rejectsDuplicateSku() {
        when(productRepository.existsBySku("VIS-001")).thenReturn(true);

        var request = new ProductRequest("vis-001", "Vis", null, null, 10);

        assertThrows(DuplicateSkuException.class, () -> service.create(request));
        verify(productRepository, never()).saveAndFlush(any());
    }
}