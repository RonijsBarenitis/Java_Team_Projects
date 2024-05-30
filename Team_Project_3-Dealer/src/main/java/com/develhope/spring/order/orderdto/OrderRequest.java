package com.develhope.spring.order.orderdto;

import com.develhope.spring.car.Vehicle;
import com.develhope.spring.order.OrderStatus;
import com.develhope.spring.user.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private Double advancePayment;
    private Boolean paidInFull;
    private Users customer;
    private Users seller;
    private Vehicle vehicle;
    private OrderStatus orderStatus;
}
