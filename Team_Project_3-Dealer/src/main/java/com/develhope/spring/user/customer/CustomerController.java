package com.develhope.spring.user.customer;

import com.develhope.spring.order.OrderInfo;
import com.develhope.spring.rent.RentInfo;
import com.develhope.spring.user.Users;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Customer options", description = "Here are all functions needed for our customers")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;


    //Creare un ordine a partire da un veicolo contrassegnato come ordinabile
    //ok
    @PostMapping("/createOrder/{idSeller}/{idVehicle}")
    public ResponseEntity<?> createOrder(@AuthenticationPrincipal Users user, @PathVariable long idSeller, @PathVariable long idVehicle, @RequestBody OrderInfo orderInfo) {
        return customerService.createOrder(user, idSeller, idVehicle, orderInfo);
    }

    //Creare un acquisto a partire da un veicolo contrassegnato come acquistabile


    //Vedere i propri ordini
    //okkk
    @GetMapping("/getOrders")
    public ResponseEntity<?> getOrders(@AuthenticationPrincipal Users customer) {
        return customerService.getOrders(customer);
    }

    //Ottenere i dettagli di un veicolo specifico
    //tested: ok
    @GetMapping("/getVehicle/{idVehicle}")
    public ResponseEntity<?> getVehicle(@AuthenticationPrincipal Users customer, @PathVariable long idVehicle) {
        return customerService.getVehicle(idVehicle);
    }


    //Cancellare un suo ordine
    //tested: ok
    @DeleteMapping("/deleteOrder/{idOrder}")
    public ResponseEntity<?> deleteOrder(@AuthenticationPrincipal Users customer, @PathVariable long idOrder) {
        return customerService.deleteOrder(customer, idOrder);
    }

    //Creare un noleggio
    //tested: ok
    @PostMapping("/createRent/{idSeller}/{idVehicle}")
    public ResponseEntity<?> createRent(@AuthenticationPrincipal Users customer, @PathVariable long idSeller, @PathVariable long idVehicle, @RequestBody RentInfo rentInfo) {
        return customerService.createRent(customer, idSeller, idVehicle, rentInfo);
    }

    //Cancellare un noleggio
    //tested: ok
    @DeleteMapping("/deleteRent/{idRent}")
    public ResponseEntity<?> deleteRent(@AuthenticationPrincipal Users customer, @PathVariable long idRent) {
        return customerService.deleteRent(customer, idRent);
    }


    //Modificare i dati dell’utente

    @PutMapping("/updateFirstName")
    public ResponseEntity<Users> updateCustomerName(@AuthenticationPrincipal Users customer, @RequestParam String firstName) {
        return customerService.updateCustomerName(customer, firstName);
    }
    @PutMapping("/updateLastName")
    public ResponseEntity<Users> updateLastName(@AuthenticationPrincipal Users customer, @RequestParam String lastName) {
        return customerService.updateLastName(customer, lastName);
    }
    @PutMapping("/updateEmail")
    public ResponseEntity<Users> updateEmail(@AuthenticationPrincipal Users customer, @RequestParam String email) {
        return customerService.updateEmail(customer, email);
    }

    @PutMapping("/updateAll")
    public ResponseEntity<Users> updateAll(@AuthenticationPrincipal Users customer, @RequestBody Users newCustomer) {
        return customerService.updateAll(customer, newCustomer);
    }

    /*
    @PutMapping("/updatePassword")
    public ResponseEntity<Users> updatePassword(@AuthenticationPrincipal Users customer, @RequestParam String password) {
        return customerService.updatePassword(customer, password);
    }
     */

    //Cancellare la propria utenza
    //TODO doesn't work
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@AuthenticationPrincipal Users customer){
        return customerService.delete(customer);
    }

    //Ricercare un veicolo secondo diversi criteri (prezzo, colore, marca, modello, ecc)
    @GetMapping("/findVehicleByAttribute")
    public ResponseEntity<?> getVehiclesByAttribute(@AuthenticationPrincipal Users customer,@RequestParam String attributeChoice,@RequestParam String attributeValue){
        return customerService.getVehicleByAttribute(attributeChoice, attributeValue);
    }

}