package com.example.taxis.repository;

import com.example.taxis.entity.Vehicle;
import org.springframework.data.jpa.repository.*;
public interface VehicleRepository extends JpaRepository<Vehicle,Long>, JpaSpecificationExecutor<Vehicle> {}
