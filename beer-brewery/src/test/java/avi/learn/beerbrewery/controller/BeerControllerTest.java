package avi.learn.beerbrewery.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import avi.learn.beerbrewery.model.Beer;
import avi.learn.beerbrewery.model.BeerStyle;
import avi.learn.beerbrewery.repository.BeerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BeerControllerTest {
	
	private static final String API_1_0_BEERS = "/api/1.0/beers";
	
	@Autowired
	TestRestTemplate testRestTemplate;
	
	@Autowired
	BeerRepository beerRepository;
	
	@Before
	public void cleanDatabase() {
		beerRepository.deleteAll();
	}
	
	@Test
	public void postTest_whenBeerIsValid_receiveOk() {
		Beer beer = createValidBeer();
		ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_BEERS, beer, Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	public void postTest_whenBeerIsValid_saveToDatabase() {
		Beer beer = createValidBeer();
		testRestTemplate.postForEntity(API_1_0_BEERS, beer, Object.class);
		List<Beer> beers = beerRepository.findAll();
		Beer beerInDB = beers.get(0);
		assertThat(beerInDB.getBeerName()).isEqualTo("test-beer");
	}
	
	@Test
	public void postTest_whenBeerNameIsLessThanRequired_receiveBadRequest() {
		Beer beer = createValidBeer();
		beer.setBeerName("abc");
		ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_BEERS, beer, Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void getTest_whenBeerDoesNotExists_receiveBadRequest() {
		ResponseEntity<Object> response = testRestTemplate.getForEntity(getURIForFirstRecord(), Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void getTest_whenBeerExists_receiveOk() {
		Beer beer = createValidBeer();
		testRestTemplate.postForEntity(API_1_0_BEERS, beer, Object.class);
		ResponseEntity<Object> response = testRestTemplate.getForEntity(getURIForFirstRecord(), Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void deleteTest_whenBeerExists_receiveOk() {
		Beer beer = createValidBeer();
		testRestTemplate.postForEntity(API_1_0_BEERS, beer, Object.class);
		testRestTemplate.delete(getURIForFirstRecord());
		List<Beer> beers = beerRepository.findAll();
		assertThat(beers.size()).isEqualTo(0);
	}
	
	@Test
	public void putTest_whenBeerExists_receiveOk() {
		Beer beer = createValidBeer();
		testRestTemplate.postForEntity(API_1_0_BEERS, beer, Object.class);
		beer.setBeerName("updated-name");
		testRestTemplate.put(getURIForFirstRecord(), beer);
		List<Beer> beers = beerRepository.findAll();
		assertThat(beers.get(0).getBeerName()).isEqualTo("updated-name");
	}

	private String getURIForFirstRecord() {
		return API_1_0_BEERS + "/" + (beerRepository.findAll().size() == 0 ? 1 
				: beerRepository.findAll().get(0).getBeerId());
	}
	
	private Beer createValidBeer() {
		Beer beer = new Beer();
		beer.setBeerName("test-beer");
		beer.setBeerStyle(BeerStyle.ALE);
		return beer;
	}

}
