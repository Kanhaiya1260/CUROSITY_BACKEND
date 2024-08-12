package com.app.dto;

import java.time.LocalDateTime;
<<<<<<< HEAD

import lombok.Data;
=======
>>>>>>> a130f28a1b28e92bf2bcf0fb7a86d1e37f486e07
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ApiResponse {
	private LocalDateTime timeStamp;
	private String message;
	public ApiResponse(String message) {
		super();
		this.message = message;
		this.timeStamp=LocalDateTime.now();
	}
<<<<<<< HEAD
=======

>>>>>>> a130f28a1b28e92bf2bcf0fb7a86d1e37f486e07
}
