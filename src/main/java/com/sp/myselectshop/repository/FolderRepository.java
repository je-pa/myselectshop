package com.sp.myselectshop.repository;

import com.sp.myselectshop.entity.Folder;
import com.sp.myselectshop.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {

  List<Folder> findAllByUserAndNameIn(User user, List<String> folderNames);

  List<Folder> findAllByUser(User user);
}
