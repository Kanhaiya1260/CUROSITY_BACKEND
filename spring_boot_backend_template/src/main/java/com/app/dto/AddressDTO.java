package com.app.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressDTO {
   @NotNull(message = "ID cannot be null")
   private Long aid;
   
   @NotNull(message = "Current address cannot be null")
   @Size(min = 1, max = 50, message = "Current address must be between 1 and 50 characters")
   private String currentAddress;
   
   @NotNull(message = "City cannot be null")
   @Size(min = 1, max = 20, message = "City must be between 1 and 20 characters")
   private String city;
   
   @NotNull(message = "Street cannot be null")
   @Size(min = 1, max = 20, message = "Street must be between 1 and 20 characters")
   private String street;
   
   @NotNull(message = "Pin code cannot be null")
   @Size(min = 6, max = 6, message = "Pin code must be exactly 6 characters")
   private String pinCode;
   
   @NotNull(message = "State cannot be null")
   @Size(min = 1, max = 50, message = "State must be between 1 and 50 characters")
   private String state;
   
   @NotNull(message = "userId cannot be null")
   private Long uid;
}
