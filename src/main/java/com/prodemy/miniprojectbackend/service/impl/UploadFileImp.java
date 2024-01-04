package com.prodemy.miniprojectbackend.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.prodemy.miniprojectbackend.service.UploadFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadFileImp implements UploadFile{

	private final Cloudinary cloudinary;
	
	@Override
	public String uploadFile(MultipartFile multipartFile) throws IOException {
		// TODO Auto-generated method stub
		return cloudinary.uploader().upload(multipartFile.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
									.get("url")
									.toString();
	}
	
}
