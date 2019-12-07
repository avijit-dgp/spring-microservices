package avi.learn.beerbrewery.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import avi.learn.beerbrewery.error.ResourceNotFoundException;
import avi.learn.beerbrewery.model.Customer;
import avi.learn.beerbrewery.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer getCustomerById(long customerId) {
		return customerRepository.findById(customerId)
				.orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public Customer createNewCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public void updateCustomer(long customerId, Customer customer) {
		Optional<Customer> fetchedCustomer = customerRepository.findById(customerId);
		if(fetchedCustomer.isPresent()) {
			Customer customerInDb = fetchedCustomer.get();
			customerInDb.setCustomerName(customer.getCustomerName());
			customerRepository.save(customerInDb);
		}
	}
	
	@Override
	public void deleteCustomerById(long customerId) {
		customerRepository.deleteById(customerId);
	}

}
