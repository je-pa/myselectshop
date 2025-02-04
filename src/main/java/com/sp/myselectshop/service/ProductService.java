package com.sp.myselectshop.service;

import com.sp.myselectshop.dto.ProductRequestDto;
import com.sp.myselectshop.dto.ProductResponseDto;
import com.sp.myselectshop.entity.Folder;
import com.sp.myselectshop.entity.Product;
import com.sp.myselectshop.dto.ProductMypriceRequestDto;
import com.sp.myselectshop.entity.ProductFolder;
import com.sp.myselectshop.entity.User;
import com.sp.myselectshop.entity.UserRoleEnum;
import com.sp.myselectshop.naver.dto.ItemDto;
import com.sp.myselectshop.repository.FolderRepository;
import com.sp.myselectshop.repository.ProductFolderRepository;
import com.sp.myselectshop.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final FolderRepository folderRepository;
  private final ProductFolderRepository productFolderRepository;

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

  @Transactional(readOnly = true)
  public Page<ProductResponseDto> getProducts(User user, int page, int size, String sortBy,
      boolean isAsc) {

    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Direction.DESC;
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    UserRoleEnum userRoleEnum = user.getRole();

    Page<Product> productList;

    if(userRoleEnum == UserRoleEnum.USER){
      productList = productRepository.findAllByUser(user, pageable);
    } else{
      productList = productRepository.findAll(pageable);
    }

    return productList.map(ProductResponseDto::new);
  }

  @Transactional
  public void updateBySearch(Long id, ItemDto itemDto) {
    Product product = productRepository.findById(id).orElseThrow(() ->
        new NullPointerException("해당 상품은 없습니다.")
    );
    product.updateByItemDto(itemDto);
  }

  public void addFolder(Long prodcutId, Long folderId, User user) {
    Product product = productRepository.findById(prodcutId).orElseThrow(
        () -> new NullPointerException("해당 상품이 존재하지 않습니다.")
    );

    Folder folder = folderRepository.findById(folderId).orElseThrow(
        () -> new NullPointerException("해당 폴더가 존재하지 않습니다.")
    );

    if(!product.getUser().getId().equals(user.getId())
     || !folder.getUser().getId().equals(user.getId())){
      throw new IllegalArgumentException("회원님의 관심상품ㅇ이 아니거나, 회원님의 폴더가 아닙니다.");
    }

    Optional<ProductFolder> overlapFolder = productFolderRepository.findByProductAndFolder(product, folder);

    if(overlapFolder.isPresent()){
      throw new IllegalArgumentException("중복된 폴더입니다.");
    }

    productFolderRepository.save(new ProductFolder(product, folder));
  }
}
