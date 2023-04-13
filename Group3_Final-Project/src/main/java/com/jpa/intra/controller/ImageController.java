package com.jpa.intra.controller;

import com.jpa.intra.domain.MailUploadFile;
import com.jpa.intra.service.MailImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {

	@Autowired
	MailImageService mailImageService;
	
	@Autowired
	ResourceLoader resourceLoader;

	// 이미지 업로드시 저장
	@PostMapping("/image")
	public ResponseEntity<?> imageUpload(@RequestParam("file") MultipartFile file) {
		try {
			MailUploadFile mailUploadFile = mailImageService.store(file);
			return ResponseEntity.ok().body("/image/" + mailUploadFile.getId());
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
	// 저장된 이미지 로드
	@GetMapping("/image/{fileId}")
	public ResponseEntity<?> serveFile(@PathVariable Long fileId){
		try {
			MailUploadFile mailUploadFile = mailImageService.load(fileId);
			Resource resource = resourceLoader.getResource("file:" + mailUploadFile.getFilePath());
			return ResponseEntity.ok().body(resource);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}

	}
}
