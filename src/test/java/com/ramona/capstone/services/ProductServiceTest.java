package com.ramona.capstone.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.ramona.capstone.dtos.ProductDto;
import com.ramona.capstone.dtos.VariantDto;
import com.ramona.capstone.entities.Brand;
import com.ramona.capstone.entities.Category;
import com.ramona.capstone.entities.Product;
import com.ramona.capstone.entities.Variant;
import com.ramona.capstone.exceptions.DuplicateSkuException;
import com.ramona.capstone.mappers.ProductMapper;
import com.ramona.capstone.repositories.BrandRepository;
import com.ramona.capstone.repositories.CategoryRepository;
import com.ramona.capstone.repositories.ProductRepository;
import com.ramona.capstone.repositories.VariantRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @Mock private ProductRepository productRepository;
  @Mock private VariantRepository variantRepository;
  @Mock private CategoryRepository categoryRepository;
  @Mock private BrandRepository brandRepository;
  @Mock private ProductMapper productMapper;

  @InjectMocks private ProductService productService;

  @Test
  @DisplayName("Should save a product and add variants successfully")
  void createProduct_Success() {

    ProductDto request = createRequest("Hyundai Elantra", "HONDA-BLK");

    Category category = new Category();
    category.setId(1L);
    category.setName("Sedans");
    category.setChildren(new ArrayList<>());

    Brand brand = new Brand();
    brand.setId(1L);
    brand.setName("Hyundai");

    // Product that mapper returns (with variant)
    Product mappedProduct = new Product();
    mappedProduct.setName("Hyundai Elantra");
    mappedProduct.setBasePrice(new BigDecimal("25000"));

    Variant variant = new Variant();
    variant.setSku("HONDA-BLK");
    variant.setPrice(new BigDecimal("24500"));
    mappedProduct.setVariants(List.of(variant));

    // Saved product (after repository save)
    Product savedProduct = new Product();
    savedProduct.setId(1L);
    savedProduct.setName("Hyundai Elantra");
    savedProduct.setCategory(category);
    savedProduct.setBrand(brand);

    ProductDto response = new ProductDto();
    response.setId(1L);
    response.setName("Hyundai Elantra");

    when(categoryRepository.findByName("Sedans")).thenReturn(Optional.of(category));
    when(brandRepository.findByName("Hyundai")).thenReturn(Optional.of(brand));
    when(productMapper.toEntity(request)).thenReturn(mappedProduct);
    when(variantRepository.existsBySku("HONDA-BLK")).thenReturn(false);
    when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
    when(productMapper.toDto(savedProduct)).thenReturn(response);

    ProductDto result = productService.createProduct(request);

    assertNotNull(result);
    assertEquals("Hyundai Elantra", result.getName());
    verify(productRepository, times(1)).save(any(Product.class));
    verify(variantRepository, times(1)).existsBySku("HONDA-BLK");
  }

  @Test
  @DisplayName("Should rollback when there is duplicate SKU")
  void createProduct_DuplicateSku_RollsBack() {

    ProductDto request = createRequest("Hyundai Elantra", "HONDA-BLK");

    Category category = new Category();
    category.setId(1L);
    category.setName("Sedans");
    category.setChildren(new ArrayList<>());

    Brand brand = new Brand();
    brand.setId(1L);
    brand.setName("Hyundai");

    Product mappedProduct = new Product();
    mappedProduct.setName("Hyundai Elantra");

    Variant variant = new Variant();
    variant.setSku("HONDA-BLK");
    mappedProduct.setVariants(List.of(variant));

    when(categoryRepository.findByName("Sedans")).thenReturn(Optional.of(category));
    when(brandRepository.findByName("Hyundai")).thenReturn(Optional.of(brand));
    when(productMapper.toEntity(request)).thenReturn(mappedProduct);
    when(variantRepository.existsBySku("HONDA-BLK")).thenReturn(true); // Duplicate!

    assertThrows(DuplicateSkuException.class, () -> productService.createProduct(request));

    verify(productRepository, never()).save(any());
  }

  private ProductDto createRequest(String name, String sku) {
    ProductDto dto = new ProductDto();
    dto.setName(name);
    dto.setCategoryName("Sedans");
    dto.setBrandName("Hyundai");
    dto.setDescription("Fuel efficient sedan");
    dto.setBasePrice(new BigDecimal("25000"));

    VariantDto variant = new VariantDto();
    variant.setSku(sku);
    variant.setPrice(new BigDecimal("24500"));
    dto.setVariants(List.of(variant));

    return dto;
  }
}
