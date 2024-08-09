package com.app.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable //=> To Tell JPA , follwoing class DOES NOT have a separate existence
//Instead the contents are going to embedded within the owning entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
//composite PK class
public class ProjectEmployeeId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long projectId;
	private Long employeeId;
}
