package com.develhope.spring.order;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.develhope.spring.car.Vehicle;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderInfo, Long> {

    List<OrderInfo> findByOrderStatus(OrderStatus status);

    // Da testare
    List<OrderInfo> findByCustomer_Id(long userId);


    Optional<OrderInfo> findByVehicleAndOrderStatus(Vehicle vehicle, OrderStatus completed);

    void deleteByCustomer_Id(Long userId);


    /*
     * @Query(value = "SELECT ORDERSTATUS FROM ORDERS o WHERE o. ")
     * List<OrderInfo> getOrdersByStatus(OrderStatus status);
     */
}
