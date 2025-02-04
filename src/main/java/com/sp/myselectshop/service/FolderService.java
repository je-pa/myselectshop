package com.sp.myselectshop.service;

import com.sp.myselectshop.dto.FolderResponseDto;
import com.sp.myselectshop.entity.Folder;
import com.sp.myselectshop.entity.User;
import com.sp.myselectshop.repository.FolderRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderService {

  private final FolderRepository folderRepository;

  public void addFolders(List<String> folderNames, User user) {
    List<Folder> exitstFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);

    List<Folder> folderList = new ArrayList<>();

    for(String folderName : folderNames) {
      if(!isExistFolderName(folderName, exitstFolderList)){
        Folder folder = new Folder(folderName, user);
        folderList.add(folder);
      }else{
        throw new IllegalArgumentException("폴더명이 중복되었습니다.");
      }
    }
    folderRepository.saveAll(folderList);
  }

  public List<FolderResponseDto> getFolders(User user) {
    List<Folder> folderList = folderRepository.findAllByUser(user);
    List<FolderResponseDto> folderResponseDtoList = new ArrayList<>();

    for(Folder folder : folderList) {
      folderResponseDtoList.add(new FolderResponseDto(folder));
    }

    return folderResponseDtoList;
  }

  private boolean isExistFolderName(String folderName, List<Folder> exitstFolderList) {
    for(Folder existFolder : exitstFolderList) {
      if(folderName.equals(existFolder.getName())) {
        return true;
      }
    }
    return false;
  }
}
