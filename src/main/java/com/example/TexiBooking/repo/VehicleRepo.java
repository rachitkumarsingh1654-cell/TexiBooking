package com.example.TexiBooking.repo;

import com.example.TexiBooking.model.Vehicle;
import com.example.TexiBooking.model.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, Integer>{
    Vehicle findByLicensePlate(String licensePlate);

    @Query("select v from Vehicle v where v.isAvailable = true order by rand() limit 1")
    Vehicle getAvailableVehiclesRandomly();

    List<Vehicle> findByVehicleType(VehicleType vehicleType);
}
