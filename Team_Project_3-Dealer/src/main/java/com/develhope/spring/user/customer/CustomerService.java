package com.develhope.spring.user.customer;

import com.develhope.spring.car.Vehicle;
import com.develhope.spring.car.VehicleRepository;

import com.develhope.spring.car.VehicleStatus;
import com.develhope.spring.order.OrderInfo;
import com.develhope.spring.order.OrderRepository;
import com.develhope.spring.order.OrderStatus;
import com.develhope.spring.rent.RentInfo;
import com.develhope.spring.rent.RentRepository;

import com.develhope.spring.role.Role;
import com.develhope.spring.role.RoleRepository;
import com.develhope.spring.user.Users;
import com.develhope.spring.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.develhope.spring.role.Role.RoleType.ROLE_CUSTOMER;
import static com.develhope.spring.role.Role.RoleType.ROLE_SELLER;

@Service
public class CustomerService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RentRepository rentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    public ResponseEntity<?> getVehicle(long idVehicle) {
        Optional<Vehicle> vehicleToFind = vehicleRepository.findById(idVehicle);
        if (vehicleToFind.isEmpty()) {
            return new ResponseEntity<>("Wrong idVehicle", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(vehicleToFind);
        }
    }


    public ResponseEntity<?> getOrders(Users customer) {
        List<OrderInfo> myorders = orderRepository.findByCustomer_Id(customer.getId());
        if (myorders.isEmpty()) {
            return new ResponseEntity<>("this customer doesn't have any orders", HttpStatus.OK);
        }
        return ResponseEntity.ok(myorders);

    }

    public ResponseEntity<?> deleteOrder(Users customer, long idOrder) {
        Optional<OrderInfo> orderToDelete = orderRepository.findById(idOrder);
        if (orderToDelete.isPresent() && orderToDelete.get().getCustomer().equals(customer)) {
            orderRepository.deleteById(idOrder);
            return ResponseEntity.ok(true);
        } else {
            return new ResponseEntity<>("Error, Wrong order", HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<?> deleteRent(Users customer, long idRent) {
        Optional<RentInfo> rentToDelete = rentRepository.findById(idRent);
        if (rentToDelete.isPresent() && rentToDelete.get().getCustomer().equals(customer)) {
            rentRepository.deleteById(idRent);
            return ResponseEntity.ok(true);
        } else {
            return new ResponseEntity<>("Error, Wrong rent", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Users> updateCustomerName(Users customer, String newFirstName) {

        customer.setFirstName(newFirstName);
        return ResponseEntity.ok(userRepository.save(customer));
    }

    public ResponseEntity<Users> updateLastName(Users customer, String newLastName) {
        customer.setLastName(newLastName);
        return ResponseEntity.ok(userRepository.save(customer));
    }

    public ResponseEntity<Users> updateEmail(Users customer, String newEmail) {
        customer.setEmail(newEmail);
        return ResponseEntity.ok(userRepository.save(customer));
    }

    public ResponseEntity<Users> updateAll(Users customer, Users newCustomer) {
        customer.setFirstName(newCustomer.getFirstName());
        customer.setLastName(newCustomer.getLastName());
        customer.setEmail(newCustomer.getEmail());
        //customer.setPassword(newCustomer.getPassword());
        return ResponseEntity.ok(userRepository.save(customer));
    }

   /* public ResponseEntity<Users> updatePassword(Users customer, String newEmail) {
        customer.setEmail(newEmail);
        return ResponseEntity.ok(userRepository.save(customer));
    }*/


    public ResponseEntity<?> createOrder(Users customer, long idSeller, long idVehicle, OrderInfo orderInfo) {
        Boolean customerCheck = checkRoleUser(customer, ROLE_CUSTOMER);
        if (!customerCheck) {
            return new ResponseEntity<>("This user is not a Customer", HttpStatus.BAD_REQUEST);
        }

        Optional<Vehicle> vehicle = vehicleRepository.findById(idVehicle);
        boolean vehicleCheck = checkVehicle(vehicle, VehicleStatus.AVAILABLE);
        if (!vehicleCheck) {
            return new ResponseEntity<>("This vehicle is not available", HttpStatus.BAD_REQUEST);
        }

        Optional<Users> seller = userRepository.findById(idSeller);
        Boolean sellerCheck = checkRoleUser(seller, ROLE_SELLER);
        if (!sellerCheck) {
            return new ResponseEntity<>("This user is not a Seller", HttpStatus.BAD_REQUEST);
        }

        OrderInfo newOrder = new OrderInfo();
        newOrder.setOrderStatus(OrderStatus.ORDERED);
        newOrder.setAdvancePayment(orderInfo.getAdvancePayment());
        newOrder.setPaidInFull(orderInfo.getPaidInFull());
        newOrder.setCustomer(customer);
        newOrder.setVehicle(vehicle.get());
        newOrder.setSeller(seller.get());
        orderRepository.save(newOrder);
        return ResponseEntity.ok(newOrder);
    }


    public ResponseEntity<?> createRent(Users customer, long idSeller, long idVehicle, RentInfo rentInfo) {
        boolean customerCheck = checkRoleUser(customer, ROLE_CUSTOMER);
        if (!customerCheck) {
            return new ResponseEntity<>("This user is not a Customer", HttpStatus.BAD_REQUEST);
        }

        Optional<Vehicle> vehicle = vehicleRepository.findById(idVehicle);
        boolean vehicleCheck = checkVehicle(vehicle, VehicleStatus.AVAILABLE);
        if (!vehicleCheck) {
            return new ResponseEntity<>("This vehicle is not available", HttpStatus.BAD_REQUEST);
        }

        Optional<Users> seller = userRepository.findById(idSeller);
        Boolean sellerCheck = checkRoleUser(seller, ROLE_SELLER);
        if (!sellerCheck) {
            return new ResponseEntity<>("This user is not a Seller", HttpStatus.BAD_REQUEST);
        }

        RentInfo newRent = new RentInfo();
        newRent.setCustomer(customer);
        newRent.setSeller(seller.get());
        newRent.setVehicle(vehicle.get());
        newRent.setStartDate(rentInfo.getStartDate());
        newRent.setEndDate(rentInfo.getEndDate());
        newRent.setIsPaid(rentInfo.getIsPaid());
        newRent.setDailyCost(rentInfo.getDailyCost());
        newRent.setTotalCost(rentInfo.getTotalCost());
        rentRepository.saveAndFlush(newRent);
        return ResponseEntity.ok(newRent);
    }

    public ResponseEntity<?> delete(Users customer) {
        orderRepository.deleteByCustomer_Id(customer.getId());
        rentRepository.deleteByCustomer_Id(customer.getId());
        roleRepository.deleteById(customer.getId());
        userRepository.deleteById(customer.getId());
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> getVehicleByAttribute(String attributeChoice, String attributeValue) {
        return switch (attributeChoice) {
            case "brand" -> ResponseEntity.ok(vehicleRepository.findByBrand(attributeValue));
            case "model" -> ResponseEntity.ok(vehicleRepository.findByModel(attributeValue));
            case "color" -> ResponseEntity.ok(vehicleRepository.findByColor(attributeValue));
            case "gearboxType" -> ResponseEntity.ok(vehicleRepository.findByGearboxType(attributeValue));
            case "fuelType" -> ResponseEntity.ok(vehicleRepository.findByFuelType(attributeValue));
            case "accessories" -> ResponseEntity.ok(vehicleRepository.findByAccessories(attributeValue));
            case "displacement" -> ResponseEntity.ok(vehicleRepository.findByDisplacement(Integer.valueOf(attributeValue)));
            case "power" -> ResponseEntity.ok(vehicleRepository.findByPower(Integer.valueOf(attributeValue)));
            case "yearOfRegistration" -> ResponseEntity.ok(vehicleRepository.findByYearOfRegistration(Integer.valueOf(attributeValue)));
            case "price" -> ResponseEntity.ok(vehicleRepository.findByPrice(Double.valueOf(attributeValue)));
            case "discount" -> ResponseEntity.ok(vehicleRepository.findByDiscount(Double.valueOf(attributeValue)));
            case "isNew" -> ResponseEntity.ok(vehicleRepository.findByIsNew(Boolean.valueOf(attributeValue)));
            default -> null;
        };
    }


    //Utilities

    public Boolean checkRoleUser(Optional<Users> user, Role.RoleType roleUser) {
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

    public Boolean checkRoleUser(Users user, Role.RoleType roleUser) {
        boolean check = false;
        if (user != null) {
            for (Role role : user.getRole()) {
                if (role.getName().equals(roleUser)) {
                    check = true;
                }
            }
        }
        return check;
    }

    public Boolean checkVehicle(Optional<Vehicle> vehicle, VehicleStatus vehicleStatus) {
        boolean vehicleCheck = false;
        if (vehicle.isPresent() && vehicle.get().getIsAvailable().equals(vehicleStatus)) {
            vehicleCheck = true;
        }
        return vehicleCheck;
    }


}

