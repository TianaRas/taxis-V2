package com.example.taxis.service;

import java.time.*;
import java.math.*;
import java.util.*;
import com.example.taxis.dto.request.*;
import com.example.taxis.dto.response.*;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
public interface ApplicationService {
    PageResponse<ClientResponse> listClient(String q,String status,String filter,Pageable pageable);
    ClientResponse getClient(Long id);
    ClientResponse createClient(@Valid ClientRequest request);
    ClientResponse updateClient(Long id,@Valid ClientRequest request);
    void deleteClient(Long id);
    PageResponse<VehicleResponse> listVehicle(String q,String status,String filter,Pageable pageable);
    VehicleResponse getVehicle(Long id);
    VehicleResponse createVehicle(@Valid VehicleRequest request);
    VehicleResponse updateVehicle(Long id,@Valid VehicleRequest request);
    void deleteVehicle(Long id);
    PageResponse<DriverResponse> listDriver(String q,String status,String filter,Pageable pageable);
    DriverResponse getDriver(Long id);
    DriverResponse createDriver(@Valid DriverRequest request);
    DriverResponse updateDriver(Long id,@Valid DriverRequest request);
    void deleteDriver(Long id);
    PageResponse<RideResponse> listRide(String q,String status,String filter,Pageable pageable);
    RideResponse getRide(Long id);
    RideResponse createRide(@Valid RideRequest request);
    RideResponse updateRide(Long id,@Valid RideRequest request);
    void deleteRide(Long id);
    List<AuditResponse> history(String resource,Long id);
    List<SettingResponse> settings();
    void setting(@Valid SettingRequest request);
    AnalyticsResponse statistics(LocalDate from,LocalDate to,Long actorId,String zone);
    void detail(String resource,Long id,Model model);
    Object perform(String resource,Long id,String operation,@Valid ActionRequest request);
}
