package com.app.dto;

import java.time.LocalDateTime;
<<<<<<< HEAD

=======
>>>>>>> f11a88456a4b114d987a9b80d747ac2657b1ed01
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

<<<<<<< HEAD
//DTO : data transfer object (used to exchange the data between 
//REST  client n REST server)
=======
>>>>>>> f11a88456a4b114d987a9b80d747ac2657b1ed01
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse {
	private String message;
	private LocalDateTime timeStamp;

	public ApiResponse(String message) {
<<<<<<< HEAD
		super();
		this.message = message;
		this.timeStamp = LocalDateTime.now();
	}
=======
		this.message = message;
		this.timeStamp = LocalDateTime.now();
	}

>>>>>>> f11a88456a4b114d987a9b80d747ac2657b1ed01
}
