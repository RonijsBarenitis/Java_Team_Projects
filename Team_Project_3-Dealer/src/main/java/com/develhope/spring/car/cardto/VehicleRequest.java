package com.develhope.spring.car.cardto;

import com.develhope.spring.car.VehicleStatus;
import com.develhope.spring.car.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequest {
    private String brand;
    private String model;
    private String color;
    private String gearboxType;
    private String fuelType;
    private String accessories;

    private Integer displacement;
    private Integer power;
    private Integer yearOfRegistration;

    private Double price;
    private Double discount;
    private Boolean isNew;
    private VehicleType vehicleType;
    private VehicleStatus isAvailable;
}
