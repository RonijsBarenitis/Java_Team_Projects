package com.develhope.spring.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByBrand(String attributeValue);

    List<Vehicle> findByModel(String attributeValue);

    List<Vehicle> findByColor(String attributeValue);

    List<Vehicle> findByGearboxType(String attributeValue);

    List<Vehicle> findByFuelType(String attributeValue);

    List<Vehicle> findByAccessories(String attributeValue);

    List<Vehicle> findByDisplacement(Integer attributeValue);

    List<Vehicle> findByPower(Integer attributeValue);

    List<Vehicle> findByYearOfRegistration(Integer attributeValue);

    List<Vehicle> findByPrice(Double attributeValue);

    List<Vehicle> findByDiscount(Double attributeValue);

    List<Vehicle> findByIsNew(Boolean attributeValue);
}

