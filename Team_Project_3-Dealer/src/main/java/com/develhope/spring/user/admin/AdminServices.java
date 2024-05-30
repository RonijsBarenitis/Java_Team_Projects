package com.develhope.spring.user.admin;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.develhope.spring.car.Vehicle;
import com.develhope.spring.car.VehicleModel;
import com.develhope.spring.car.VehicleRepository;
import com.develhope.spring.car.VehicleStatus;
import com.develhope.spring.car.cardto.VehicleRequest;
import com.develhope.spring.car.cardto.VehicleResponse;
import com.develhope.spring.configurations.exception.OrderRentCreationException;
import com.develhope.spring.order.OrderInfo;
import com.develhope.spring.order.OrderModel;
import com.develhope.spring.order.OrderRepository;
import com.develhope.spring.order.OrderStatus;
import com.develhope.spring.order.orderdto.OrderRequest;
import com.develhope.spring.order.orderdto.OrderResponse;
import com.develhope.spring.purchase.PurchaseInfo;
import com.develhope.spring.purchase.PurchaseModel;
import com.develhope.spring.purchase.PurchaseRepository;
import com.develhope.spring.purchase.purchasedto.PurchaseRequest;
import com.develhope.spring.purchase.purchasedto.PurchaseResponse;
import com.develhope.spring.rent.RentInfo;
import com.develhope.spring.rent.RentModel;
import com.develhope.spring.rent.RentRepository;
import com.develhope.spring.rent.rentdto.RentRequest;
import com.develhope.spring.rent.rentdto.RentResponse;
import com.develhope.spring.role.Role.RoleType;
import com.develhope.spring.user.Users;
import com.develhope.spring.user.UserRepository;

@Service
public class AdminServices {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RentRepository rentRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    // --------------- logica dei controller per operazioni sui veicoli
    // -------------

    // tutti i veicoli
    public List<VehicleResponse> getVehicle() {
        List<Vehicle> response = vehicleRepository.findAll();
        List<VehicleResponse> result = new ArrayList<>();
        for (Vehicle vehicle : response) {
            VehicleModel vehicleModel = VehicleModel.mapEntityToModel(vehicle);
            result.add(VehicleModel.mapModelToResponse(vehicleModel));
        }
        return result;
    }

    // aggiunzione di un veicolo
    public VehicleResponse addVehicle(VehicleRequest newVehicleRequest) {
        VehicleModel vehicleRequestModel = VehicleModel.mapRequestToModel(newVehicleRequest);
        Vehicle vehicleRequestEntity = VehicleModel.mapModelToEntity(vehicleRequestModel);
        Vehicle savedVehicle = vehicleRepository.saveAndFlush(vehicleRequestEntity);
        VehicleModel vehicleResponseModel = VehicleModel.mapEntityToModel(savedVehicle);
        return VehicleModel.mapModelToResponse(vehicleResponseModel);
    }

    // metodo per la modifica di un solo parametro del veicolo
    public VehicleResponse modifyVehicle(Long id, String choice, VehicleRequest vehicleRequest) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicleRequestEntity = optionalVehicle.get();
            switch (choice) {
                case "brand":
                    vehicleRequestEntity.setBrand(vehicleRequest.getBrand());
                    break;
                case "model":
                    vehicleRequestEntity.setModel(vehicleRequest.getModel());
                    break;
                case "color":
                    vehicleRequestEntity.setColor(vehicleRequest.getColor());
                    break;
                case "gear":
                    vehicleRequestEntity.setGearboxType(vehicleRequest.getGearboxType());
                    break;
                case "fuel":
                    vehicleRequestEntity.setFuelType(vehicleRequest.getFuelType());
                    break;
                case "accessories":
                    vehicleRequestEntity.setAccessories(vehicleRequest.getAccessories());
                    break;
                case "displacement":
                    vehicleRequestEntity.setDisplacement(vehicleRequest.getDisplacement());
                    break;
                case "power":
                    vehicleRequestEntity.setPower(vehicleRequest.getPower());
                    break;
                case "year_registration":
                    vehicleRequestEntity.setYearOfRegistration(vehicleRequest.getYearOfRegistration());
                    break;
                case "price":
                    vehicleRequestEntity.setPrice(vehicleRequest.getPrice());
                    break;
                case "discount":
                    vehicleRequestEntity.setDiscount(vehicleRequest.getDiscount());
                    break;
                case "new/used":
                    vehicleRequestEntity.setIsNew(vehicleRequest.getIsNew());
                    break;
                case "all":
                    modifyAllPartOfVehicle(vehicleRequestEntity, vehicleRequest);
                    break;
                default:

                    break;
            }
            Vehicle modifiedVehicle = vehicleRepository.saveAndFlush(vehicleRequestEntity);
            VehicleModel modifiedVehicleModel = VehicleModel.mapEntityToModel(modifiedVehicle);
            VehicleResponse vehicleResponse = VehicleModel.mapModelToResponse(modifiedVehicleModel);
            return vehicleResponse;
        }
        return null;
    }

    // metodo per la modifica di tutti i parametri del veicolo
    // ok
    private void modifyAllPartOfVehicle(Vehicle vehicleRequestEntity, VehicleRequest vehicleRequest) {
        vehicleRequestEntity.setBrand(vehicleRequest.getBrand());
        vehicleRequestEntity.setModel(vehicleRequest.getModel());
        vehicleRequestEntity.setColor(vehicleRequest.getColor());
        vehicleRequestEntity.setGearboxType(vehicleRequest.getGearboxType());
        vehicleRequestEntity.setFuelType(vehicleRequest.getFuelType());
        vehicleRequestEntity.setAccessories(vehicleRequest.getAccessories());
        vehicleRequestEntity.setDisplacement(vehicleRequest.getDisplacement());
        vehicleRequestEntity.setPower(vehicleRequest.getPower());
        vehicleRequestEntity.setYearOfRegistration(vehicleRequest.getYearOfRegistration());
        vehicleRequestEntity.setPrice(vehicleRequest.getPrice());
        vehicleRequestEntity.setDiscount(vehicleRequest.getDiscount());
        vehicleRequestEntity.setIsNew(vehicleRequest.getIsNew());
    }

    // eliminazione di un veicolo
    // ok
    public boolean deleteVehicle(Long id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // cambio dello Status di un veicolo
    public VehicleResponse changeStatusVehicle(Long id, VehicleStatus status) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle updatedVehicle = optionalVehicle.get();
            updatedVehicle.setIsAvailable(status);
            Vehicle modifiedVehicle = vehicleRepository.saveAndFlush(updatedVehicle);
            VehicleModel updatedVehicleModel = VehicleModel.mapEntityToModel(modifiedVehicle);
            return VehicleModel.mapModelToResponse(updatedVehicleModel);
        }
        return null;
    }

    // --------------- logica dei controller per operazioni sui ordini -------------

    // ottenere tutti gli ordini
    public List<OrderResponse> getOrder() {
        List<OrderInfo> response = orderRepository.findAll();
        List<OrderResponse> result = new ArrayList<>();
        for (OrderInfo order : response) {
            OrderModel orderModel = OrderModel.mapEntityToModel(order);
            result.add(OrderModel.mapModelToResponse(orderModel));
        }
        return result;
    }

    // creazione ordine per un utente tramite id
    public OrderResponse createOrderForAUser(Long user_id, Long vehicle_id, boolean advance)
            throws OrderRentCreationException {
        Optional<Users> user = userRepository.findById(user_id);
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicle_id);
        if (!user.isPresent() || !vehicle.isPresent()) {
            throw new OrderRentCreationException("Invalid user or vehicle ID");
        }
        if (vehicle.get().getIsAvailable() != VehicleStatus.AVAILABLE) {
            throw new OrderRentCreationException("Vehicle is not available for order");
        }
        OrderRequest newOrderRequest = new OrderRequest();
        newOrderRequest.setVehicle(vehicle.get());
        // se l'acquirente paga un anticipo si chiama il metodo getAdvancePaymentAmount
        // altrimenti si restituisce null
        // su advance payment
        newOrderRequest.setAdvancePayment(advance ? getAdvancePaymentAmount(vehicle_id) : null);
        newOrderRequest.setPaidInFull(false);
        newOrderRequest.setCustomer(user.get());
        newOrderRequest.setOrderStatus(OrderStatus.INCOMPLETE);
        try {
            vehicle.get().setIsAvailable(VehicleStatus.ORDERED);
            vehicleRepository.save(vehicle.get());

            OrderModel orderModel = OrderModel.mapRequestToModel(newOrderRequest);
            OrderInfo orderInfo = orderRepository.save(OrderModel.mapModelToEntity(orderModel));
            OrderResponse orderResponse = OrderModel.mapModelToResponse(OrderModel.mapEntityToModel(orderInfo));

            return orderResponse;
        } catch (Exception e) {
            throw new OrderRentCreationException("Failed to create order");
        }
    }

    // calcola un aticipo di base che è uguale al trenta percento del costo
    // dell'auto
    private Double getAdvancePaymentAmount(Long vehicle_id) {
        Vehicle vehicle = vehicleRepository.getReferenceById(vehicle_id);
        double totalAmount = vehicle.getPrice() * 0.3;
        return totalAmount;
    }

    // eliminazione di un ordine per un cliente tramite id
    public boolean deleteOrder(Long id) {
        Optional<OrderInfo> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            OrderInfo order = optionalOrder.get();
            Long vehicleId = order.getVehicle().getVehicleId();
            orderRepository.deleteById(id);

            Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicleId);
            if (optionalVehicle.isPresent()) {
                Vehicle vehicle = optionalVehicle.get();
                vehicle.setIsAvailable(VehicleStatus.AVAILABLE);
                vehicleRepository.save(vehicle);
            }
            return true;
        }
        return false;
    }

    // modifica di un ordine
    public OrderResponse modifyOrderBy(Long id, String choice, OrderRequest orderRequest,
            Long customerId, Long sellerId, Long vehicleId) {
        Optional<OrderInfo> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            OrderInfo orderRequestEntity = optionalOrder.get();

            boolean isValidCustomer = checkUserRoles(customerId, RoleType.ROLE_CUSTOMER);
            boolean isValidSeller = checkUserRoles(sellerId, RoleType.ROLE_SELLER);
            if (!isValidCustomer || !isValidSeller) {
                return null;
            }

            switch (choice) {
                case "paidInFull":
                    orderRequestEntity.setPaidInFull(orderRequest.getPaidInFull());
                    break;
                case "orderStatus":
                    orderRequestEntity.setOrderStatus(orderRequest.getOrderStatus());
                    break;
                case "AdvancePayment":
                    orderRequestEntity.setAdvancePayment(orderRequest.getAdvancePayment());
                    break;
                case "Customer":
                    Users customer = userRepository.getReferenceById(customerId);
                    orderRequestEntity.setCustomer(customer);
                    break;
                case "Seller":
                    Users seller = userRepository.getReferenceById(sellerId);
                    orderRequestEntity.setSeller(seller);
                    break;
                case "Vehicle":
                    Vehicle vehicle = vehicleRepository.getReferenceById(vehicleId);
                    orderRequestEntity.setVehicle(vehicle);
                    break;
                case "all":
                    modifyAllPartOfOrder(orderRequestEntity, orderRequest);
                    break;
                default:

                    break;
            }
            OrderInfo modifiedOrder = orderRepository.saveAndFlush(orderRequestEntity);
            OrderModel modifiedOrderModel = OrderModel.mapEntityToModel(modifiedOrder);
            OrderResponse orderResponse = OrderModel.mapModelToResponse(modifiedOrderModel);
            return orderResponse;
        }
        return null;
    }

    private boolean checkUserRoles(Long userId, RoleType requiredRole) {
        Optional<Users> user = userRepository.findById(userId);
        return user.isPresent() && user.get().getRole().stream()
                .anyMatch(role -> role.getName() == requiredRole);
    }

    // modifica di tutti i parametri un ordine
    private void modifyAllPartOfOrder(OrderInfo orderRequestEntity, OrderRequest orderRequest) {
        orderRequestEntity.setAdvancePayment(orderRequest.getAdvancePayment());
        orderRequestEntity.setCustomer(orderRequest.getCustomer());
        orderRequestEntity.setOrderStatus(orderRequest.getOrderStatus());
        orderRequestEntity.setPaidInFull(orderRequest.getPaidInFull());
        orderRequestEntity.setSeller(orderRequest.getSeller());
        orderRequestEntity.setVehicle(orderRequest.getVehicle());
    }

    // --------------- logica dei controller per operazioni sui noleggi
    // -------------

    // ottenere tutti i noleggi
    public List<RentResponse> getallRent() {
        List<RentInfo> response = rentRepository.findAll();
        List<RentResponse> result = new ArrayList<>();
        for (RentInfo rent : response) {
            RentModel rentModel = RentModel.mapEntityToModel(rent);
            result.add(RentModel.mapModelToResponse(rentModel));
        }
        return result;
    }

    // creazione noleggio per un utente tramite id
    public RentResponse createRentForAUser(Long user_id, Long vehicle_id, String startDate,
            String endDate,
            Double dailyCost) throws OrderRentCreationException {
        Optional<Users> user = userRepository.findById(user_id);
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicle_id);
        if (!user.isPresent() || !vehicle.isPresent()) {
            throw new OrderRentCreationException("Invalid user or vehicle ID");
        }
        if (vehicle.get().getIsAvailable() != VehicleStatus.AVAILABLE) {
            throw new OrderRentCreationException("Vehicle is not available for rent");
        }
        RentRequest newRentRequest = new RentRequest();
        newRentRequest.setVehicle(vehicle.get());
        newRentRequest.setCustomer(user.get());
        newRentRequest.setStartDate(startDate);
        newRentRequest.setEndDate(endDate);
        // il costo giornaliero deve essere implementato con una logica simile
        // all'anticipo
        // quindi è da cambiare
        newRentRequest.setDailyCost(dailyCost);
        newRentRequest.setTotalCost(calculateTotalCost(startDate, endDate, dailyCost));
        newRentRequest.setIsPaid(false);
        try {

            vehicle.get().setIsAvailable(VehicleStatus.NOT_AVAILABLE);
            vehicleRepository.save(vehicle.get());

            RentModel rentModel = RentModel.mapRequestToModel(newRentRequest);
            RentInfo rentInfo = rentRepository.save(RentModel.mapModelToEntity(rentModel));
            RentResponse rentResponse = RentModel.mapModelToResponse(RentModel.mapEntityToModel(rentInfo));
            return rentResponse;

        } catch (Exception e) {
            throw new OrderRentCreationException("Failed to create rent ");
        }
    }

    // calcolo totale del noleggio
    private Double calculateTotalCost(String startDate, String endDate, Double dailyCost) {
        OffsetDateTime rentStartDate = OffsetDateTime.parse(startDate);
        OffsetDateTime rentEndDate = OffsetDateTime.parse(endDate);

        Duration duration = Duration.between(rentStartDate, rentEndDate);
        long rentalDays = duration.toDays();

        double totalCost = rentalDays * dailyCost;
        return totalCost;
    }

    // eliminazione di un noleggio per un cliente tramite id
    public boolean deleteRent(Long id) {
        Optional<RentInfo> optionalRent = rentRepository.findById(id);
        if (optionalRent.isPresent()) {
            RentInfo rent = optionalRent.get();
            Long vehicleId = rent.getVehicle().getVehicleId();
            rentRepository.deleteById(id);

            Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicleId);
            if (optionalVehicle.isPresent()) {
                Vehicle vehicle = optionalVehicle.get();
                vehicle.setIsAvailable(VehicleStatus.AVAILABLE);
                vehicleRepository.save(vehicle);
            }
            return true;
        }
        return false;
    }

    // modifica di un noleggio
    public RentResponse modifyRentById(Long id, String choice, RentRequest rentRequest, Long customerId,
            Long vehicleId, Long sellerId) {
        Optional<RentInfo> optionalRent = rentRepository.findById(id);
        if (optionalRent.isPresent()) {

            RentInfo rentRequestEntity = optionalRent.get();
            boolean isValidCustomer = checkUserRoles(customerId, RoleType.ROLE_CUSTOMER);
            boolean isValidSeller = checkUserRoles(sellerId, RoleType.ROLE_SELLER);
            if (!isValidCustomer || !isValidSeller) {
                return null;
            }
            switch (choice) {
                case "dailyCost":
                    rentRequestEntity.setDailyCost(rentRequest.getDailyCost());
                    break;
                case "startDate":
                    rentRequestEntity.setStartDate(rentRequest.getStartDate());
                    break;
                case "endDate":
                    rentRequestEntity.setEndDate(rentRequest.getEndDate());
                    break;
                case "totalCost":
                    rentRequestEntity.setEndDate(rentRequest.getEndDate());
                    break;
                case "isPaid":
                    rentRequestEntity.setEndDate(rentRequest.getEndDate());
                    break;
                case "vehicle":
                    rentRequestEntity.setEndDate(rentRequest.getEndDate());
                    break;
                case "customer":
                    Users customer = userRepository.getReferenceById(customerId);
                    rentRequestEntity.setCustomer(customer);
                    break;
                case "seller":
                    Users seller = userRepository.getReferenceById(sellerId);
                    rentRequestEntity.setSeller(seller);
                    break;
                case "Vehicle":
                    Vehicle vehicle = vehicleRepository.getReferenceById(vehicleId);
                    rentRequestEntity.setVehicle(vehicle);
                    break;
                case "all":
                    modifyAllPartOfRent(rentRequestEntity, rentRequest);
                    break;
                default:

                    break;
            }
            RentInfo modifiedRent = rentRepository.saveAndFlush(rentRequestEntity);
            RentModel modifiedRentModel = RentModel.mapEntityToModel(modifiedRent);
            RentResponse rentResponse = RentModel.mapModelToResponse(modifiedRentModel);

            return rentResponse;
        }
        return null;
    }

    // modifica di tutti i parametri di un noleggio
    private void modifyAllPartOfRent(RentInfo rentRequestEntity, RentRequest rentRequest) {
        rentRequestEntity.setStartDate(rentRequest.getStartDate());
        rentRequestEntity.setEndDate(rentRequest.getEndDate());
        rentRequestEntity.setDailyCost(rentRequest.getDailyCost());
        rentRequestEntity.setTotalCost(rentRequest.getTotalCost());
        rentRequestEntity.setIsPaid(rentRequest.getIsPaid());
        rentRequestEntity.setVehicle(rentRequest.getVehicle());
        rentRequestEntity.setCustomer(rentRequest.getCustomer());
        rentRequestEntity.setSeller(rentRequest.getSeller());
    }

    // --------------- logica dei controller per operazioni sugli acquisti
    // -------------

    // ottieni tutti gli acquisti
    public List<PurchaseResponse> getPurchase() {
        List<PurchaseInfo> response = purchaseRepository.findAll();
        List<PurchaseResponse> result = new ArrayList<>();
        for (PurchaseInfo purchase : response) {
            PurchaseModel purchaseModel = PurchaseModel.mapEntityToModel(purchase);
            result.add(PurchaseModel.mapModelToResponse(purchaseModel));
        }
        return result;
    }

    // crea un acquisto per un utente
    public PurchaseResponse createPurchaseForAUser(Long id, Long vehicle_Id) {
        Optional<Users> user = userRepository.findById(id);
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicle_Id);
        if (!user.isPresent() || !vehicle.isPresent()) {
            throw new OrderRentCreationException("Invalid user or vehicle ID");
        }
        if (vehicle.get().getIsAvailable() != VehicleStatus.AVAILABLE
                && vehicle.get().getIsAvailable() != VehicleStatus.ORDERED) {
            throw new OrderRentCreationException("Vehicle is not available for purchase or is already bought");
        }

        Optional<OrderInfo> order = orderRepository.findByVehicleAndOrderStatus(vehicle.get(),
                OrderStatus.COMPLETED);
        if (order.isPresent() && order.get().getCustomer().equals(user.get())) {
            return purchaseIfOrderExist(vehicle, order);
        } else {
            return purchaseWithoutOrder(user, vehicle);
        }

    }

    private PurchaseResponse purchaseWithoutOrder(Optional<Users> user, Optional<Vehicle> vehicle) {
        PurchaseRequest newPurchaseRequest = new PurchaseRequest();
        newPurchaseRequest.setCustomer(user.get());
        newPurchaseRequest.setVehicle(vehicle.get());
        newPurchaseRequest.setTotalPrice(vehicle.get().getPrice());

        PurchaseModel newPurchaseModel = PurchaseModel.mapRequestToModel(newPurchaseRequest);
        try {
            vehicle.get().setIsAvailable(VehicleStatus.BOUGHT);
            vehicleRepository.save(vehicle.get());

            PurchaseInfo newPurchaseInfo = PurchaseModel.mapModelToEntity(newPurchaseModel);
            PurchaseInfo savedPurchaseInfo = purchaseRepository.save(newPurchaseInfo);
            PurchaseResponse purchaseResponse = PurchaseModel.mapModelToResponse(
                    PurchaseModel.mapEntityToModel(savedPurchaseInfo));

            return purchaseResponse;
        } catch (Exception e) {
            throw new OrderRentCreationException("Failed to create purchase ");
        }
    }

    public PurchaseResponse purchaseIfOrderExist(Optional<Vehicle> vehicle, Optional<OrderInfo> order) {
        PurchaseRequest purchaseRequest = new PurchaseRequest();
        purchaseRequest.setCustomer(order.get().getCustomer());
        purchaseRequest.setVehicle(order.get().getVehicle());
        purchaseRequest.setTotalPrice(vehicle.get().getPrice() - order.get().getAdvancePayment());
        purchaseRequest.setOrder(order.get());

        PurchaseModel newPurchaseModel = PurchaseModel.mapRequestToModel(purchaseRequest);
        try {
            vehicle.get().setIsAvailable(VehicleStatus.BOUGHT);
            vehicleRepository.save(vehicle.get());

            PurchaseInfo newPurchaseInfo = PurchaseModel.mapModelToEntity(newPurchaseModel);
            PurchaseInfo savedPurchaseInfo = purchaseRepository.save(newPurchaseInfo);
            PurchaseResponse purchaseResponse = PurchaseModel.mapModelToResponse(
                    PurchaseModel.mapEntityToModel(savedPurchaseInfo));

            return purchaseResponse;
        } catch (Exception e) {
            throw new OrderRentCreationException("Failed to create purchase with order");
        }
    }

    // elimina un acquisto per un utente
    public boolean deletePurchase(Long id) {
        Optional<PurchaseInfo> optionalPurchase = purchaseRepository.findById(id);
        if (optionalPurchase.isPresent()) {
            PurchaseInfo purchase = optionalPurchase.get();
            Long vehicleId = purchase.getVehicle().getVehicleId();
            purchaseRepository.deleteById(id);

            Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicleId);
            if (optionalVehicle.isPresent()) {
                Vehicle vehicle = optionalVehicle.get();
                vehicle.setIsAvailable(VehicleStatus.AVAILABLE);
                vehicleRepository.save(vehicle);
            }
            return true;
        }
        return false;
    }

    // modifica un acquisto per un utente
    // public ResponseEntity<Object> modifyPurchaseById(Long id, Long userId, String
    // choice, PurchaseInfo purchase) {
    // switch (choice) {
    // case "totalPrice":

    // break;
    // case "customer":

    // break;

    // case "vehicle":

    // break;

    // default:
    // break;
    // }
    // }

}
