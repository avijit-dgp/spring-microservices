package avi.learn.beerbrewery.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import avi.learn.beerbrewery.error.ResourceNotFoundException;
import avi.learn.beerbrewery.model.Beer;
import avi.learn.beerbrewery.repository.BeerRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
	
	@Autowired
	private BeerRepository beerRepository;

	@Override
	public Beer getBeerById(long beerId) {
		return beerRepository.findById(beerId)
				.orElseThrow(ResourceNotFoundException :: new);
	}

	@Override
	public Beer createNewBeer(Beer beer) {
		return beerRepository.save(beer);
	}

	@Override
	public void updateBeer(long beerId, Beer beer) {
		Optional<Beer> fetchedBeer = beerRepository.findById(beerId);
		if(fetchedBeer.isPresent()) {
			Beer beerInDb = fetchedBeer.get();
			beerInDb.setBeerName(beer.getBeerName());
			beerInDb.setBeerStyle(beer.getBeerStyle());
			beerRepository.save(beerInDb);
		}
	}

	@Override
	public void deleteBeerById(long beerId) {
		log.debug("deleting beer with id: " + beerId);
		beerRepository.deleteById(beerId);
	}

}
