package com.app.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {
	private LocalDateTime timeStamp;
	private String message;
	private String status="success";
	private T data;
	public ApiResponse(String message) {
		super();
		this.message = message;
		this.timeStamp=LocalDateTime.now();
	}

	public ApiResponse(String status, String message) {
		this.status = status;
		this.message = message;
		this.timeStamp = LocalDateTime.now();
	}
	public ApiResponse(String status,String message,T data) {
		this.status = status;
		this.message = message;
		this.data = data;
		this.timeStamp = LocalDateTime.now();
	}
}
