package com.jpa.intra.controller;

import com.jpa.intra.domain.FileEntity;
import com.jpa.intra.domain.MailUploadFile;
import com.jpa.intra.domain.Member;
import com.jpa.intra.repository.File_Repository;
import com.jpa.intra.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final File_Repository fileRepository;
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("files") List<MultipartFile> files,HttpServletRequest request) throws IOException {

        for (MultipartFile multipartFile : files) {
            fileService.uploadFile(multipartFile, request);


        }

        return "redirect:/moveDrive";
    }







    @GetMapping("/deleteFile")
    public String deleteFile(@RequestParam("path")String path) throws IOException{

        fileService.deleteFile(path);

        return "redirect:/moveDrive";
    }

    @GetMapping("/getTeamDrive")
    public String getTeamDrive(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Member m = (Member) session.getAttribute("user");

        List<FileEntity> flist = fileRepository.findFilelistByTeamName(m.getTeam().getTeam_name());

        model.addAttribute("page", "드라이브");
        model.addAttribute("fileList" , flist);

        return "drive/main";
    }

    @GetMapping("/downloadFile") //파일 다운로드 로직
    public String downloadFile(@RequestParam Long id) throws IOException{
        fileService.fileDownload(id); //경로 지정할수 있게 해줘도 좋을듯 .
        return "redirect:/moveDrive";
    }


}
