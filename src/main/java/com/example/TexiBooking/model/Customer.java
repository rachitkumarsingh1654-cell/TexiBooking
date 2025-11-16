package com.example.TexiBooking.model;

import com.example.TexiBooking.model.enums.Gender;
import jakarta.persistence.Enumerated;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "customers")
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    private String customerName;
    private int customerAge;

    @Email
    @Column(unique = true, nullable = false)
    private String emailId;

    private String customerPhone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<Booking> bookings = new ArrayList<>();
}
