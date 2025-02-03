package com.sp.myselectshop.controller;

import com.sp.myselectshop.dto.ProductRequestDto;
import com.sp.myselectshop.dto.ProductResponseDto;
import com.sp.myselectshop.dto.ProductMypriceRequestDto;
import com.sp.myselectshop.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

  private final ProductService productService;

  @PostMapping("/products")
  public ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto) {
    return productService.createProduct(productRequestDto);
  }

  @PutMapping("/products/{id}")
  public ProductResponseDto updateProduct(@PathVariable int id, @RequestBody ProductMypriceRequestDto productRequestDto) {
    return productService.updateProduct(id, productRequestDto);
  }

  @GetMapping("/products")
  public List<ProductResponseDto> getProducts() {
    return productService.getProducts();
  }
}
