package com.app.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.validator.constraints.Length;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long accountId;
	@Column(nullable = false)
	@Length(min = 12 ,max = 12)
	private String accountNo;
	@Column(nullable = false )
	private String IFSCCode;
	@Column(nullable = false , length = 40)
	private String branch;
	@Column(nullable = false , length = 40)
	private String bankName;
}
