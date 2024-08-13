package com.app.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long aid;
	@Column(name = "current_address" ,length = 50 , nullable = false)
	private String currentAddress;
	@Column(length = 20 , nullable = false)
	private String city;
	@Column(length = 20 , nullable = false)
	private String street;
	@Column(length = 50 , nullable = false)
	private String state;
	@Column(name = "pin_code" ,nullable = false)
	@Length(min = 6,max = 6)
	private String pinCode;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uid")
	@JsonBackReference
	private User user;
}
