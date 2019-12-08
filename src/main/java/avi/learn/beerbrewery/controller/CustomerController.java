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

import avi.learn.beerbrewery.model.Customer;
import avi.learn.beerbrewery.service.CustomerService;

@RestController
@RequestMapping("/api/1.0/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getCustomer(@PathVariable long customerId) {
		return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> createNewCustomer(@RequestBody @Valid Customer customer) {
		Customer createdCustomer = customerService.createNewCustomer(customer);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location" , "/api/1.0/customers/" + createdCustomer.getCustomerId());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	@PutMapping("/{customerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateCustomer(@PathVariable long customerId, @RequestBody @Valid Customer customer) {
		customerService.updateCustomer(customerId, customer);
	}
	
	@DeleteMapping("/{customerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCustomer(@PathVariable long customerId) {
		customerService.deleteCustomerById(customerId);
	}

}
