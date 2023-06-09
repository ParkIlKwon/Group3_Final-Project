package com.jpa.intra.service;

import com.jpa.intra.domain.MailUploadFile;
import com.jpa.intra.repository.MailUploadFile_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MailImageService {

	@Autowired
	MailUploadFile_Repository mailUploadFileRepository;
	
	private final Path rootLocation;

	public MailImageService(String uploadPath) {
		this.rootLocation = Paths.get(uploadPath);
		System.out.println(rootLocation.toString());
	}
	
	public MailUploadFile store(MultipartFile file) throws Exception {

		try {
			if(file.isEmpty()) {
				throw new Exception("Failed to store empty file " + file.getOriginalFilename());
			}
			
			String saveFileName = fileSave(rootLocation.toString(), file);
			MailUploadFile saveFile = new MailUploadFile();
			saveFile.setFileName(file.getOriginalFilename());
			saveFile.setSaveFileName(saveFileName);
			saveFile.setContentType(file.getContentType());
			saveFile.setSize(file.getResource().contentLength());
			saveFile.setRegisterDate(LocalDateTime.now());
			saveFile.setFilePath(rootLocation.toString().replace(File.separatorChar, '/') +'/' + saveFileName);
			mailUploadFileRepository.save(saveFile);
			return saveFile;
			
		} catch(IOException e) {
			throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
		}
		
		
	}

	public MailUploadFile load(Long fileId) {
		return mailUploadFileRepository.findById(fileId).get();
	}
	
	public String fileSave(String rootLocation, MultipartFile file) throws IOException {
		File uploadDir = new File(rootLocation);
		
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		
		// saveFileName 생성
		UUID uuid = UUID.randomUUID();
		String saveFileName = uuid.toString() + file.getOriginalFilename();
		File saveFile = new File(rootLocation, saveFileName);
		FileCopyUtils.copy(file.getBytes(), saveFile);
		
		return saveFileName;
	}
	
	
	
}
