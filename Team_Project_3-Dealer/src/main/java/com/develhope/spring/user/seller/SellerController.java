package com.develhope.spring.user.seller;

import com.develhope.spring.car.Vehicle;
import com.develhope.spring.car.VehicleRepository;

import com.develhope.spring.order.OrderInfo;
import com.develhope.spring.order.OrderRepository;
import com.develhope.spring.rent.RentInfo;
import com.develhope.spring.rent.RentService;

import com.develhope.spring.user.Users;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Seller options", description = "Here are all functions needed for our sellers")
@RestController
@RequestMapping("/seller")
public class SellerController {

        @Autowired
        private VehicleRepository vehicleRepository;

        @Autowired
        private OrderRepository orderRepository;

        @Autowired
        private SellerService sellerService;

        @Autowired
        private RentService rentService;

        @Operation(summary = "Retrieve a vehicle by ID.", description = "Get a Vehicle object by specifying its ID.", tags = {
                        "seller", "get", "vehicle" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Request completed successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
                        @ApiResponse(responseCode = "404", description = "No vehicle with that ID") })
        @GetMapping("/vehicleinfo/{vehicleId}")
        public Optional<Vehicle> getOneVehicle(@PathVariable Long vehicleId) {
                return sellerService.getOneVehicleById(vehicleId);
        }

        @Operation(summary = "Create order.", description = "Create an order for a vehicle, if it is available, by specifying order details and vehicle ID", tags = {
                        "seller", "create", "order" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Order created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid vehicle ID or order details supplied"),
                        @ApiResponse(responseCode = "404", description = "No vehicle with that ID") })
        @PostMapping("/createorder/{customerId}/{vehicleId}")
        public ResponseEntity<String> createOrder(@AuthenticationPrincipal Users seller, @PathVariable Long customerId, @PathVariable Long vehicleId, @RequestBody OrderInfo newOrder) {
                return sellerService.createOrderOfAvailableVehicle(seller, customerId, vehicleId, newOrder);
        }

        @Operation(summary = "Delete order.", description = "Delete an order by specifying its ID", tags = { "seller",
                        "delete", "order" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Order deleted successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid vehicle ID or order details supplied"),
                        @ApiResponse(responseCode = "404", description = "No vehicle with that ID") })
        @DeleteMapping("/deleteorder/{id}")
        public void deleteOrder(@PathVariable Long id) {
                orderRepository.deleteById(id);
        }

        @Operation(summary = "Modify order.", description = "Modify an order by specifying its ID and updated order details", tags = {
                        "seller", "modify", "order" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Order modified successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid order ID or order details supplied"),
                        @ApiResponse(responseCode = "404", description = "No order with that ID") })
        @PutMapping("/modifyorder/{idOrderToModify}")
        public ResponseEntity<String> modifyOrder(@PathVariable Long idOrderToModify, @RequestBody OrderInfo modifiedOrder) {
                return sellerService.modifyOrder(idOrderToModify, modifiedOrder);
        }

        @Operation(summary = "Get order status.", description = "Get order status details by specifying its ID", tags = {
                        "seller", "get", "order" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Request completed successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid order ID supplied"),
                        @ApiResponse(responseCode = "404", description = "No order with that ID") })
        @GetMapping("/orderstatus/{orderId}")
        public ResponseEntity<String> getOrderStatus(@PathVariable Long orderId) {
                return sellerService.getOrderStatus(orderId);
        }

        @Operation(summary = "Update order status.", description = "Update order status by specifying its ID and the updated order status", tags = {
                        "seller", "update", "order" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Request completed successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid order ID or order status supplied"),
                        @ApiResponse(responseCode = "404", description = "No order with that ID or invalid order status") })
        @PatchMapping("/updateorderstatus/{orderId}/{newStatus}")
        public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @PathVariable String newStatus) {
                return sellerService.updateOrderStatus(orderId, newStatus);
        }

        @GetMapping("/getordersbystatus/{status}")
        public ResponseEntity<String> getOrdersByStatus(@PathVariable String status){
                return sellerService.findOrdersByStatus(status);
        }

        @PostMapping("/createrentorder/{customerId}/{vehicleId}")
        public ResponseEntity<String> createRentOrder(@AuthenticationPrincipal Users seller,@PathVariable Long customerId,@PathVariable Long vehicleId, @RequestBody RentInfo newRentOrder) {
                return sellerService.createRent(seller, customerId, vehicleId, newRentOrder);
        }

        @DeleteMapping("/deleterentorder/{rentId}")
        public void deleteRentOrder(@PathVariable Long rentId) {
                rentService.deleteRent(rentId);
        }

        @PutMapping("/modifyrentorder/{orderId}")
        public ResponseEntity<String> modifyRentOrder(@PathVariable Long orderId, @RequestBody RentInfo updatedRentOrder) {
                return sellerService.updateRent(orderId, updatedRentOrder);
        }
}
