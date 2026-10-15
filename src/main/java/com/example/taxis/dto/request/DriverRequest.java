package com.example.taxis.dto.request;

import java.time.*;
import java.math.*;
import java.util.*;
import jakarta.validation.constraints.*;
public record DriverRequest(
    @NotBlank @Size(max=2000) String name,
    @NotBlank @Size(max=2000) String phone,
    @NotBlank @Size(max=2000) String licenseNumber,
    @NotNull LocalDate licenseExpiry,
    @NotNull LocalDate hiredAt,
    @NotNull @Positive Long vehicleId,
    @NotNull @PositiveOrZero BigDecimal distanceToPickup,
    @NotBlank @Size(max=2000) @Pattern(regexp="DISPONIBLE|EN_PAUSE|INDISPONIBLE|SUSPENDU") String status
) {}
