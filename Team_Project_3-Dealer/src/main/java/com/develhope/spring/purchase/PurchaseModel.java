package com.develhope.spring.purchase;

import com.develhope.spring.car.Vehicle;
import com.develhope.spring.order.OrderInfo;
import com.develhope.spring.purchase.purchasedto.PurchaseRequest;
import com.develhope.spring.purchase.purchasedto.PurchaseResponse;
import com.develhope.spring.user.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseModel {

    private Long purchaseId;
    private Double totalPrice;
    private OrderInfo order;
    private Users customer;
    private Vehicle vehicle;

    public static PurchaseModel mapEntityToModel(PurchaseInfo purchaseInfo){
        return new PurchaseModel(
                purchaseInfo.getPurchaseId(),
                purchaseInfo.getTotalPrice(),
                purchaseInfo.getOrder(),
                purchaseInfo.getCustomer(),
                purchaseInfo.getVehicle()
        );
    }
    public static PurchaseInfo mapModelToEntity(PurchaseModel purchaseModel){
        return new PurchaseInfo(
                purchaseModel.getPurchaseId(),
                purchaseModel.getTotalPrice(),
                purchaseModel.getOrder(),
                purchaseModel.getCustomer(),
                purchaseModel.getVehicle()
        );
    }
    public static PurchaseResponse mapModelToResponse(PurchaseModel purchaseModel){
        return new PurchaseResponse(
                purchaseModel.getPurchaseId(),
                purchaseModel.getTotalPrice(),
                purchaseModel.getOrder(),
                purchaseModel.getCustomer(),
                purchaseModel.getVehicle()
        );
    }
    public static PurchaseModel mapRequestToModel(PurchaseRequest purchaseRequest){
        return new PurchaseModel(
                null,
                purchaseRequest.getTotalPrice(),
                purchaseRequest.getOrder(),
                purchaseRequest.getCustomer(),
                purchaseRequest.getVehicle()
        );
    }
}
