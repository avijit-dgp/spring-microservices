package avi.learn.beerbrewery.service;

import avi.learn.beerbrewery.model.Beer;

public interface BeerService {
	Beer getBeerById(long beerId);

	Beer createNewBeer(Beer beer);

	void updateBeer(long beerId, Beer beer);

	void deleteBeerById(long beerId);
}
