package com.example.taxis.repository;

import com.example.taxis.entity.Driver;
import org.springframework.data.jpa.repository.*;
public interface DriverRepository extends JpaRepository<Driver,Long>, JpaSpecificationExecutor<Driver> {}
