package com.app.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imgid;
	
	@ManyToOne
	@JoinColumn(name="pid")
	private Product product;
			
	private int stock;
	
	@Column(length = 15)
	private String color;
}
