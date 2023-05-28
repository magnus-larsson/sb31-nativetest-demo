package com.example.tcdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@SpringBootApplication
public class TcdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TcdemoApplication.class, args);
	}
}

@Controller
@ResponseBody
class CustomerController {

	private final CustomerRepository customerRepository;

	CustomerController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@GetMapping("/customers")
	Iterable<Customer> customers() {
		return customerRepository.findAll();
	}
}

interface CustomerRepository extends CrudRepository<Customer, Integer> {
}

record Customer(Integer id, String name) {}


