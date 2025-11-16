package com.example.TexiBooking.repo;
import com.example.TexiBooking.model.Booking;
import com.example.TexiBooking.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Integer>{

    // write native query from cab-2 lecture
    // as we know int sql both driver & Vehicle has relationship
    @Query(value = "select * from drivers where vehicle_id = :id ", nativeQuery = true)
    Driver getDriverByVehicleId(@Param("id") int id);

    List<Driver> findByVehicleIsAvailable(boolean isAvailable);

    Driver findByBookings(Booking b);
}
