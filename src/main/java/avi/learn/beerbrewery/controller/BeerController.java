package avi.learn.beerbrewery.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import avi.learn.beerbrewery.model.Beer;
import avi.learn.beerbrewery.service.BeerService;

@RestController
@RequestMapping("/api/1.0/beers")
public class BeerController {
	
	@Autowired
	private BeerService beerService;
	
	@GetMapping("/{beerId}")
	public ResponseEntity<Beer> getBeer(@PathVariable long beerId) {
		return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> createNewBeer(@RequestBody @Valid Beer beer) {
		Beer createdBeer = beerService.createNewBeer(beer);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Location", "/api/1.0/beers/" + createdBeer.getBeerId());
		return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
	}
	
	@PutMapping("/{beerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateBeer(@PathVariable long beerId, @RequestBody @Valid Beer beer) {
		beerService.updateBeer(beerId, beer);
	}
	
	@DeleteMapping("/{beerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBeer(@PathVariable long beerId) {
		beerService.deleteBeerById(beerId);
	}

}
