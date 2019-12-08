package avi.learn.beerbrewery.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Beer {
	@Id
	@GeneratedValue
	private long beerId;
	@NotNull
	@Size(min = 4, max = 255)
	private String beerName;
	private BeerStyle beerStyle;
}
