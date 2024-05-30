package com.develhope.spring.rent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentService {
    @Autowired
    private RentRepository rentRepository;

    //NuovoNoleggio Service
    public RentInfo createRent(RentInfo rent){
        return rentRepository.save(rent);
    }

    //Tutti Noleggi Service
    public List<RentInfo> getAllRents(){
        return rentRepository.findAll();
    }

    //Noleggio tramite id (ricerca) Service
    public Optional<RentInfo> getRentByID(Long id){
        return rentRepository.findById(id);
    }

    //Update rent tramite Body Service
    public RentInfo updateRent(Long id,RentInfo rentDetails){
        Optional<RentInfo> rent = rentRepository.findById(id);
        if(rent.isPresent()){
            RentInfo updatedRent = rent.get();
          //  updatedRent.setStartDate(rentDetails.getStartDate());
          //  updatedRent.setEndDate(rentDetails.getEndDate());
            updatedRent.setDailyCost(rentDetails.getDailyCost());
            updatedRent.setTotalCost(rentDetails.getTotalCost());
            updatedRent.setIsPaid(rentDetails.getIsPaid());
            return rentRepository.save(updatedRent);
        }else{
            //Noleggio non presente
            throw new RuntimeException("Rent not found with id : " + id);
        }
    }

    //elimina noleggio su id Service
    public void deleteRent(Long id){
        rentRepository.deleteById(id);
    }
}