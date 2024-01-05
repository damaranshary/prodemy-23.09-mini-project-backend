package com.prodemy.miniprojectbackend.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFile {
	String uploadFile(MultipartFile multipartFile) throws IOException;
}
