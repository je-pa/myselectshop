package com.sp.myselectshop.controller;

import com.sp.myselectshop.dto.FolderRequestDto;
import com.sp.myselectshop.dto.FolderResponseDto;
import com.sp.myselectshop.security.UserDetailsImpl;
import com.sp.myselectshop.service.FolderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FolderController {

  private final FolderService folderService;

  @PostMapping("/folders")
  public void addFolders(@RequestBody FolderRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    List<String> folderNames = requestDto.getFolderNames();
    folderService.addFolders(folderNames, userDetails.getUser());
  }

  /**
   * 폴더 전체 조회
   * @param userDetails
   * @return
   */
  @GetMapping("/folders")
  public List<FolderResponseDto> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    return folderService.getFolders(userDetails.getUser());
  }
}
