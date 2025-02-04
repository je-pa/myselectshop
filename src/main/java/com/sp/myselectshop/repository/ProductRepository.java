package com.sp.myselectshop.repository;

import com.sp.myselectshop.dto.ProductResponseDto;
import com.sp.myselectshop.entity.Product;
import com.sp.myselectshop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Page<Product> findAllByUser(User user, Pageable pageable);

  // selct * from product p list join product_folder pf on p.id = pf.product_id where p.user_id = 1 and pf.folder_id = 3;
  Page<Product> findAllByUserAndProductFolderList_FolderId(User user, Long folderId, Pageable pageable);
}
