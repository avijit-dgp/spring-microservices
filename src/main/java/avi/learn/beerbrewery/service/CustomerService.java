package avi.learn.beerbrewery.service;

import avi.learn.beerbrewery.model.Customer;

public interface CustomerService {

	Customer getCustomerById(long customerId);

	Customer createNewCustomer(Customer customer);

	void updateCustomer(long customerId, Customer customer);

	void deleteCustomerById(long customerId);

}
