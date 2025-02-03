package com.sp.myselectshop.repository;

import com.sp.myselectshop.entity.Product;
import com.sp.myselectshop.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findAllByUser(User user);
}
