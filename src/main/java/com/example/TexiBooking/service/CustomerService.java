package com.example.TexiBooking.service;
import com.example.TexiBooking.dto.request.CustomerRequest;
import com.example.TexiBooking.dto.response.ApiResponse;
import com.example.TexiBooking.dto.response.CustomerResponse;
import com.example.TexiBooking.exception.ResourceNotFoundException;
import com.example.TexiBooking.model.Customer;
import com.example.TexiBooking.model.enums.Gender;
import com.example.TexiBooking.repo.CustomerRepo;
import com.example.TexiBooking.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepo customerRepo;

    public CustomerResponse addCustomer(CustomerRequest customerRequest) {

        // dtoToObj
        Customer customer = customerRepo.findByEmailId(customerRequest.getEmailId());
        if (customer != null)
            throw new RuntimeException("Customer Already Present !!");
        Customer newCustomer = CustomerTransformer.customerRequestToCustomer(customerRequest);
        Customer saved = customerRepo.save(newCustomer);
        // obj to DTO
        return CustomerTransformer.customerToCustomerResponse(saved);
    }

    public CustomerResponse getCustomerById(int id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Id", id));
        // obj to ResponseDTO
        return CustomerTransformer.customerToCustomerResponse(customer);
    }

    public List<CustomerResponse> getAllByGender(Gender gender) {
        List<Customer> customers = customerRepo.findByGender(gender);
        List<CustomerResponse> customerResponses = new ArrayList<>();
        // entity to response DTO
        for (Customer customer : customers) {
            customerResponses.add(CustomerTransformer.customerToCustomerResponse(customer));
        }
        return customerResponses;
    }

    public List<CustomerResponse> getAllByGenderAndAge(Gender gender, int age) {
        List<Customer> customers = customerRepo.findByGenderAndCustomerAge(gender, age);
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for (Customer customer : customers) {
            customerResponses.add(CustomerTransformer.customerToCustomerResponse(customer));
        }
        return customerResponses;
    }

    public List<CustomerResponse> getAllByGenderAndGreaterThanAge(Gender gender, int age) {
        List<Customer> customers = customerRepo.getAllByGenderAndGreaterThanAge(gender, age);
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for (Customer customer : customers) {
            customerResponses.add(CustomerTransformer.customerToCustomerResponse(customer));
        }
        return customerResponses;
    }

    public Customer updateCustomer(Integer customerId, CustomerRequest customerRequest) {
        Customer existingCustomer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Id", customerId));
        if (existingCustomer != null) {
            existingCustomer.setCustomerPhone(customerRequest.getCustomerPhone());
            existingCustomer.setCustomerName(customerRequest.getCustomerName());
            existingCustomer.setEmailId(customerRequest.getEmailId());
            existingCustomer.setCustomerAge(customerRequest.getCustomerAge());
            return customerRepo.save(existingCustomer);
        } else {
            throw new ResourceNotFoundException("Customer", "Id", customerId);
        }
    }

    public ApiResponse deleteCustomer(Integer customerId) {
        Optional<Customer> existingCustomerOptional = customerRepo.findById(customerId);
        if (existingCustomerOptional.isPresent()) {
            customerRepo.deleteById(customerId);

            return new ApiResponse("Resource Deleted Successfully !", true,
                    "Resource with ID " + customerId + " has been removed permanently.", null);
        } else {
            throw new ResourceNotFoundException("Customer", "Id", customerId);
        }
    }
}
