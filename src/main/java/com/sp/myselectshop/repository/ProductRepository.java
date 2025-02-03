package com.sp.myselectshop.repository;

import com.sp.myselectshop.entity.Product;
import com.sp.myselectshop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Page<Product> findAllByUser(User user, Pageable pageable);
}
