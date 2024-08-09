package com.app.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ApiResponse;
import com.app.entities.Employee;

public interface ImageHandlingService {
	ApiResponse uploadImage(Long empId, MultipartFile image) throws IOException;
	byte[] serveImage(Long empId) throws IOException;
	//used for uploading img along with emp details
	void uploadImage(Employee emp, MultipartFile image) throws IOException;
}
