package com.app.Entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariant {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imgid;
			
	private int stock;
	
	@Column(length = 15)
	private String color;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="pid")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="uid")
	private User user;
	
	private int rating;
	
}
