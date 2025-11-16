package com.example.TexiBooking.model;
import com.example.TexiBooking.model.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "vehicles")
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String model;
    @Column(unique = true)
    private String licensePlate;
    private String color;
    private double perKmRate;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    private boolean isAvailable;
}
