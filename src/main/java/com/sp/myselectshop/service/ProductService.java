package com.sp.myselectshop.service;

import com.sp.myselectshop.dto.ProductRequestDto;
import com.sp.myselectshop.dto.ProductResponseDto;
import com.sp.myselectshop.entity.Product;
import com.sp.myselectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
    Product product = productRepository.save(new Product(productRequestDto));
    return new ProductResponseDto(product);
  }
}
