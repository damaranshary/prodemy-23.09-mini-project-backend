package com.prodemy.miniprojectbackend.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.prodemy.miniprojectbackend.service.UploadFile;

@Service
public class UploadFileImpl implements UploadFile{

	@Autowired
	private Cloudinary cloudinary;
	
	@Override
	public String uploadFile(MultipartFile multipartFile) throws IOException {
		// TODO Auto-generated method stub
		return this.cloudinary.uploader().upload(multipartFile.getBytes(), Map.of())
									.get("url")
									.toString();
	}
	
}
