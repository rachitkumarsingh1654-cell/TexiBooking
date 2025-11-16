package com.example.TexiBooking.repo;

import com.example.TexiBooking.model.Booking;
import com.example.TexiBooking.model.Customer;
import com.example.TexiBooking.model.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer>{
    List<Customer> findByGender(Gender gender);

    List<Customer> findByGenderAndCustomerAge(Gender gender, int age);

    @Query(value = "select c from Customer c where gender = :gender And customerAge > :age")
    List<Customer> getAllByGenderAndGreaterThanAge(@Param("gender") Gender gender, @Param("age") int age);

    Customer findByEmailId(String emailId);

    Customer findByBookings(Booking b);
}
