package com.develhope.spring.car;

import com.develhope.spring.car.cardto.VehicleRequest;
import com.develhope.spring.car.cardto.VehicleResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleModel {
    private Long vehicleId;
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

    public static VehicleModel mapEntityToModel(Vehicle vehicleInfo) {
        return new VehicleModel(
                vehicleInfo.getVehicleId(),
                vehicleInfo.getBrand(),
                vehicleInfo.getModel(),
                vehicleInfo.getColor(),
                vehicleInfo.getGearboxType(),
                vehicleInfo.getFuelType(),
                vehicleInfo.getAccessories(),
                vehicleInfo.getDisplacement(),
                vehicleInfo.getPower(),
                vehicleInfo.getYearOfRegistration(),
                vehicleInfo.getPrice(),
                vehicleInfo.getDiscount(),
                vehicleInfo.getIsNew(),
                vehicleInfo.getVehicleType(),
                vehicleInfo.getIsAvailable()
        );
    }

    public static Vehicle mapModelToEntity(VehicleModel vehicleModel) {
        return new Vehicle(
                vehicleModel.getVehicleId(),
                vehicleModel.getBrand(),
                vehicleModel.getModel(),
                vehicleModel.getColor(),
                vehicleModel.getGearboxType(),
                vehicleModel.getFuelType(),
                vehicleModel.getAccessories(),
                vehicleModel.getDisplacement(),
                vehicleModel.getPower(),
                vehicleModel.getYearOfRegistration(),
                vehicleModel.getPrice(),
                vehicleModel.getDiscount(),
                vehicleModel.getIsNew(),
                vehicleModel.getVehicleType(),
                vehicleModel.getIsAvailable()
        );
    }

    public static VehicleResponse mapModelToResponse(VehicleModel vehicleModel) {
        return new VehicleResponse(
                vehicleModel.getVehicleId(),
                vehicleModel.getBrand(),
                vehicleModel.getModel(),
                vehicleModel.getColor(),
                vehicleModel.getGearboxType(),
                vehicleModel.getFuelType(),
                vehicleModel.getAccessories(),
                vehicleModel.getDisplacement(),
                vehicleModel.getPower(),
                vehicleModel.getYearOfRegistration(),
                vehicleModel.getPrice(),
                vehicleModel.getDiscount(),
                vehicleModel.getIsNew(),
                vehicleModel.getVehicleType(),
                vehicleModel.getIsAvailable()
        );
    }

    public static VehicleModel mapRequestToModel(VehicleRequest vehicleRequest) {
        return new VehicleModel(
                null,
                vehicleRequest.getBrand(),
                vehicleRequest.getModel(),
                vehicleRequest.getColor(),
                vehicleRequest.getGearboxType(),
                vehicleRequest.getFuelType(),
                vehicleRequest.getAccessories(),
                vehicleRequest.getDisplacement(),
                vehicleRequest.getPower(),
                vehicleRequest.getYearOfRegistration(),
                vehicleRequest.getPrice(),
                vehicleRequest.getDiscount(),
                vehicleRequest.getIsNew(),
                vehicleRequest.getVehicleType(),
                vehicleRequest.getIsAvailable()
                );
    }


}
