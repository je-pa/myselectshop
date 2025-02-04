package com.sp.myselectshop.repository;

import com.sp.myselectshop.entity.Folder;
import com.sp.myselectshop.entity.Product;
import com.sp.myselectshop.entity.ProductFolder;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductFolderRepository extends JpaRepository<ProductFolder, Long> {

  Optional<ProductFolder> findByProductAndFolder(Product product, Folder folder);
}
