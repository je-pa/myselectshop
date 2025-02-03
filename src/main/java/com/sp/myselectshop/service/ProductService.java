package com.sp.myselectshop.service;

import com.sp.myselectshop.dto.ProductRequestDto;
import com.sp.myselectshop.dto.ProductResponseDto;
import com.sp.myselectshop.entity.Product;
import com.sp.myselectshop.dto.ProductMypriceRequestDto;
import com.sp.myselectshop.entity.User;
import com.sp.myselectshop.naver.dto.ItemDto;
import com.sp.myselectshop.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public static final int MIN_MY_PRICE = 100;

  public ProductResponseDto createProduct(ProductRequestDto productRequestDto, User user) {
    Product product = productRepository.save(new Product(productRequestDto, user));
    return new ProductResponseDto(product);
  }

  @Transactional
  public ProductResponseDto updateProduct(Long id, ProductMypriceRequestDto productRequestDto) {
    int myPrice = productRequestDto.getMyprice();
    if(myPrice < MIN_MY_PRICE){
      throw new IllegalArgumentException("최소 " + MIN_MY_PRICE + "원 이상으로 설정해 주세요.");
    }
    Product product = productRepository.findById(id).orElseThrow(()->
          new NullPointerException("해당 상품을 찾을 수 없습니다.")
        );
    product.update(productRequestDto);

    return new ProductResponseDto(product);
  }

  public List<ProductResponseDto> getProducts(User user) {
    List<Product> productList = productRepository.findAllByUser(user);
    List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

    for (Product product : productList) {
      productResponseDtoList.add(new ProductResponseDto(product));
    }

    return productResponseDtoList;
  }

  public List<ProductResponseDto> getAllProducts() {
    List<Product> productList = productRepository.findAll();
    List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

    for (Product product : productList) {
      productResponseDtoList.add(new ProductResponseDto(product));
    }

    return productResponseDtoList;
  }

  @Transactional
  public void updateBySearch(Long id, ItemDto itemDto) {
    Product product = productRepository.findById(id).orElseThrow(() ->
        new NullPointerException("해당 상품은 없습니다.")
    );
    product.updateByItemDto(itemDto);
  }
}
