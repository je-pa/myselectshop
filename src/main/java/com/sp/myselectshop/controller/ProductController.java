package com.sp.myselectshop.controller;

import com.sp.myselectshop.dto.ProductRequestDto;
import com.sp.myselectshop.dto.ProductResponseDto;
import com.sp.myselectshop.dto.ProductMypriceRequestDto;
import com.sp.myselectshop.security.UserDetailsImpl;
import com.sp.myselectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

  private final ProductService productService;

  @PostMapping("/products")
  public ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto
      , @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return productService.createProduct(productRequestDto, userDetails.getUser());
  }

  @PutMapping("/products/{id}")
  public ProductResponseDto updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto productRequestDto) {
    return productService.updateProduct(id, productRequestDto);
  }

  @GetMapping("/products")
  public Page<ProductResponseDto> getProducts(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortBy") String sortBy,
      @RequestParam("isAsc") boolean isAsc,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return productService.getProducts(userDetails.getUser(),
        page-1, size, sortBy, isAsc
        );
  }

  @PostMapping("/products/{prodcutId}/folder")
  public void addFolder(
      @PathVariable Long prodcutId,
      @RequestParam Long folderId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    productService.addFolder(prodcutId, folderId, userDetails.getUser());
  }

  @GetMapping("/folders/{folderId}/products")
  public Page<ProductResponseDto> getProductsInFolder(
      @PathVariable Long folderId,
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortBy") String sortBy,
      @RequestParam("isAsc") boolean isAsc,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ){
    return productService.getProductsInFolder(
        folderId,
        page-1,
        size,
        sortBy,
        isAsc,
        userDetails.getUser()
    );
  }
}
