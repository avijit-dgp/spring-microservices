package avi.learn.beerbrewery.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Customer {
	@Id
	@GeneratedValue
	private long customerId;
	@NotNull
	@Size(min = 4, max = 255)
	private String customerName;
	private String emailId;
}
