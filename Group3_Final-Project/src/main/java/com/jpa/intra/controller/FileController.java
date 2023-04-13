package com.jpa.intra.controller;

import com.jpa.intra.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("files") List<MultipartFile> files,HttpServletRequest request) throws IOException {

        for (MultipartFile multipartFile : files) {
            fileService.uploadFile(multipartFile, request);
        }

        return "redirect:/moveDrive";
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("path")String path) throws IOException{
//        System.out.println(path);
        fileService.deleteFile(path);

        return "redirect:/moveDrive";
    }


}
