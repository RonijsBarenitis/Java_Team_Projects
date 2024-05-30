package com.develhope.spring.rent;

import com.develhope.spring.car.Vehicle;
import com.develhope.spring.rent.rentdto.RentRequest;
import com.develhope.spring.rent.rentdto.RentResponse;
import com.develhope.spring.user.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentModel {

    private Long rentId;

    private String startDate;
    private String endDate;
    private Double dailyCost;
    private Double totalCost;
    private Boolean isPaid;
    private Vehicle vehicle;
    private Users customer;
    private Users seller;

    public static RentModel mapEntityToModel(RentInfo rentInfo){
        return new RentModel(
                rentInfo.getRentId(),
                rentInfo.getStartDate(),
                rentInfo.getEndDate(),
                rentInfo.getDailyCost(),
                rentInfo.getTotalCost(),
                rentInfo.getIsPaid(),
                rentInfo.getVehicle(),
                rentInfo.getCustomer(),
                rentInfo.getSeller()
        );
    }
    public static RentInfo mapModelToEntity(RentModel rentModel){
        return new RentInfo(
                rentModel.getRentId(),
                rentModel.getStartDate(),
                rentModel.getEndDate(),
                rentModel.getDailyCost(),
                rentModel.getTotalCost(),
                rentModel.getIsPaid(),
                rentModel.getVehicle(),
                rentModel.getCustomer(),
                rentModel.getSeller()
        );
    }
    public static RentResponse mapModelToResponse(RentModel rentModel){
        return new RentResponse(
                rentModel.getRentId(),
                rentModel.getStartDate(),
                rentModel.getEndDate(),
                rentModel.getDailyCost(),
                rentModel.getTotalCost(),
                rentModel.getIsPaid(),
                rentModel.getVehicle(),
                rentModel.getCustomer(),
                rentModel.getSeller()
        );
    }
    public static RentModel mapRequestToModel(RentRequest rentRequest){
        return new RentModel(
                null,
                rentRequest.getStartDate(),
                rentRequest.getEndDate(),
                rentRequest.getDailyCost(),
                rentRequest.getTotalCost(),
                rentRequest.getIsPaid(),
                rentRequest.getVehicle(),
                rentRequest.getCustomer(),
                rentRequest.getSeller()
        );
    }


}
