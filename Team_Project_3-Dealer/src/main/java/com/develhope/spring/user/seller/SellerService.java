package com.develhope.spring.user.seller;

import com.develhope.spring.car.Vehicle;
import com.develhope.spring.car.VehicleRepository;
import com.develhope.spring.car.VehicleStatus;
import com.develhope.spring.order.OrderInfo;
import com.develhope.spring.order.OrderRepository;
import com.develhope.spring.order.OrderStatus;

import com.develhope.spring.rent.RentInfo;
import com.develhope.spring.rent.RentModel;
import com.develhope.spring.rent.RentRepository;
import com.develhope.spring.rent.rentdto.RentRequest;
import com.develhope.spring.rent.rentdto.RentResponse;
import com.develhope.spring.role.Role;
import com.develhope.spring.user.UserRepository;
import com.develhope.spring.user.Users;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class SellerService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<Vehicle> getOneVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    public OrderInfo getOneOrderById(Long id) {
        return orderRepository.getById(id);
    }

    public ResponseEntity<String> createOrderOfAvailableVehicle(Users seller, Long customerId, Long vehicleId, OrderInfo order) {
        try {
            Optional<Vehicle> vehicleToOrder = vehicleRepository.findById(vehicleId);
            Optional<Users> supposedCustomer = userRepository.findById(customerId);
            Boolean checkCustomer = checkRole(supposedCustomer, Role.RoleType.ROLE_CUSTOMER);

            if (supposedCustomer.isEmpty() || !checkCustomer){
                return ResponseEntity.status(HttpStatusCode.valueOf(403)).body("Invalid customer ID");
            } else if(vehicleToOrder.isPresent() && vehicleToOrder.get().getIsAvailable() == VehicleStatus.AVAILABLE) {
                OrderInfo newOrder = new OrderInfo();
                newOrder.setAdvancePayment(order.getAdvancePayment());
                newOrder.setPaidInFull(order.getPaidInFull());
                newOrder.setOrderStatus(order.getOrderStatus());
                newOrder.setCustomer(supposedCustomer.get());
                newOrder.setSeller(seller);
                newOrder.setVehicle(vehicleToOrder.get());
                vehicleToOrder.get().setIsAvailable(VehicleStatus.NOT_AVAILABLE);
                orderRepository.save(newOrder);
                return ResponseEntity.ok("Order placed successfully");
            } else if (vehicleToOrder.get().getIsAvailable() != VehicleStatus.AVAILABLE) {
                return ResponseEntity.status(HttpStatusCode.valueOf(406)).body("This vehicle is not available");
            }
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Something went wrong in the function body");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Vehicle with ID[" +vehicleId +"] doesn't exist");
        }
    }

    public ResponseEntity<String> modifyOrder(Long orderId, OrderInfo newOrder) {
        OrderInfo orderToModify = orderRepository.getById(orderId);
        if (orderToModify != null) {
            orderToModify.setAdvancePayment(newOrder.getAdvancePayment());
            orderToModify.setPaidInFull(newOrder.getPaidInFull());
            orderToModify.setOrderStatus(newOrder.getOrderStatus());
            orderRepository.save(orderToModify);
            return ResponseEntity.ok("Order modified successfully!");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Vehicle with ID[" +orderId +"] doesn't exist");
    }

    public ResponseEntity<String> getOrderStatus(Long orderId) {
        Optional<OrderInfo> checkOrder = orderRepository.findById(orderId);
        if(checkOrder.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Order with ID[" +orderId +"] doesn't exist");
        }
        return ResponseEntity.ok("The status of order ID[" +orderId +"] is [" +checkOrder.get().getOrderStatus() +"]");
    }

    public ResponseEntity<String> updateOrderStatus(Long orderId, String newOrderStatus) {
        OrderInfo orderToUpdateStatus = orderRepository.getById(orderId);
        boolean checkStatus = EnumUtils.isValidEnum(OrderStatus.class, newOrderStatus);
        if(!checkStatus){
            return ResponseEntity.status(HttpStatusCode.valueOf(406)).body("[" +newOrderStatus +"] is not a valid Order status");
        }
        OrderStatus status = orderRepository.getById(orderId).getOrderStatus();
        if(orderToUpdateStatus == null){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Order with ID[" +orderId +"] doesn't exist");
        } else if (EnumUtils.isValidEnum(OrderStatus.class, newOrderStatus.toString())) {
            orderToUpdateStatus.setOrderStatus(status);
            orderRepository.save(orderToUpdateStatus);
            return ResponseEntity.ok("Status changed successfully");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Something went wrong");
    }

    public ResponseEntity<String> findOrdersByStatus(String stringStatus) {
        boolean checkStatus = EnumUtils.isValidEnum(OrderStatus.class, stringStatus);
        if(!checkStatus){
            return ResponseEntity.status(HttpStatusCode.valueOf(406)).body("[" +stringStatus +"] is not a valid Order status");
        }
        OrderStatus status = OrderStatus.valueOf(stringStatus);
        return ResponseEntity.ok("All [" +status +"] orders are: \n" +orderRepository.findByOrderStatus(status));
    }

    public ResponseEntity<String> createRent(Users seller, Long customerId, Long vehicleId, RentInfo newRentOrder) {
        try {
            Optional<Vehicle> vehicleToRent = vehicleRepository.findById(vehicleId);
            Optional<Users> supposedCustomer = userRepository.findById(customerId);
            Boolean checkCustomer = checkRole(supposedCustomer, Role.RoleType.ROLE_CUSTOMER);

            if (supposedCustomer.isEmpty() || !checkCustomer){
                return ResponseEntity.status(HttpStatusCode.valueOf(403)).body("Invalid customer ID");
            } else if(vehicleToRent.isPresent() && vehicleToRent.get().getIsAvailable() == VehicleStatus.RENTABLE) {
                RentRequest newRentRequest = new RentRequest();
                newRentRequest.setVehicle(vehicleToRent.get());
                newRentRequest.setCustomer(supposedCustomer.get());
                newRentRequest.setSeller(seller);
                newRentRequest.setStartDate(newRentOrder.getStartDate());
                newRentRequest.setEndDate(newRentOrder.getEndDate());
                newRentRequest.setDailyCost(newRentRequest.getDailyCost());
                newRentRequest.setIsPaid(false);
                vehicleToRent.get().setIsAvailable(VehicleStatus.RENTED);

                String startDate = newRentOrder.getStartDate();
                String endDate = newRentOrder.getEndDate();
                Double dailyCost = newRentOrder.getDailyCost();
                newRentRequest.setTotalCost(calculateTotalCost(startDate, endDate, dailyCost));

                RentModel rentModel = RentModel.mapRequestToModel(newRentRequest);
                RentInfo rentInfo = rentRepository.save(RentModel.mapModelToEntity(rentModel));
                RentResponse rentResponse = RentModel.mapModelToResponse(RentModel.mapEntityToModel(rentInfo));

                return ResponseEntity.ok("Rent order placed successfully\n" +rentResponse);
            } else if (vehicleToRent.get().getIsAvailable() != VehicleStatus.RENTABLE) {
                return ResponseEntity.status(HttpStatusCode.valueOf(406)).body("This vehicle is not rentable");
            }
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Something went wrong in the function body");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Vehicle with ID[" +vehicleId +"] doesn't exist");
        }
    }

    public ResponseEntity<String> updateRent(Long rentId, RentInfo updatedRentOrder) {
        RentInfo rentToModify = rentRepository.getById(rentId);
        if (rentToModify != null) {
            RentRequest newRentRequest = new RentRequest();
            newRentRequest.setStartDate(updatedRentOrder.getStartDate());
            newRentRequest.setEndDate(updatedRentOrder.getEndDate());
            newRentRequest.setDailyCost(newRentRequest.getDailyCost());
            newRentRequest.setIsPaid(false);

            RentModel rentModel = RentModel.mapRequestToModel(newRentRequest);
            RentInfo rentInfo = rentRepository.save(RentModel.mapModelToEntity(rentModel));
            RentResponse rentResponse = RentModel.mapModelToResponse(RentModel.mapEntityToModel(rentInfo));
            return ResponseEntity.ok("Rent modified successfully!/n" +rentResponse);
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Rent order with ID[" +rentId +"] doesn't exist");
    }


    //Utilities

    public Boolean checkRole(Optional<Users> user, Role.RoleType roleUser) {
        boolean check = false;
        if (user.isPresent()) {
            for (Role role : user.get().getRole()) {
                if (role.getName().equals(roleUser)) {
                    check = true;
                }
            }
        }
        return check;
    }

    private Double calculateTotalCost(String startDate, String endDate, Double dailyCost) {
        OffsetDateTime rentStartDate = OffsetDateTime.parse(startDate);
        OffsetDateTime rentEndDate = OffsetDateTime.parse(endDate);

        Duration duration = Duration.between(rentStartDate, rentEndDate);
        long rentalDays = duration.toDays();

        return rentalDays * dailyCost;
    }
}
