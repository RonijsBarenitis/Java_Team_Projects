package com.develhope.spring.rent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rent")
public class RentController {

    @Autowired
    private RentService rentService;

    //NuovoNoleggio
    @PostMapping("/create")
    public ResponseEntity<RentInfo> createRent(@RequestBody RentInfo rent){
        RentInfo newRent = rentService.createRent(rent);
        return new ResponseEntity<>(newRent, HttpStatus.CREATED);
    }

    //Tutti i noleggi
    @GetMapping("/all")
    public ResponseEntity<List<RentInfo>> getAllRents() {
        List<RentInfo> rents = rentService.getAllRents();
        return new ResponseEntity<>(rents, HttpStatus.OK);
    }

    // Recupero di un noleggio per ID
    @GetMapping("/{id}")
    public ResponseEntity<RentInfo> getRentById(@PathVariable Long id) {
        Optional<RentInfo> rent = rentService.getRentByID(id);
        return rent.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Aggiornamento di un noleggio
    @PutMapping("/update/{id}")
    public ResponseEntity<RentInfo> updateRent(@PathVariable Long id, @RequestBody RentInfo rentDetails) {
        try {
            RentInfo updatedRent = rentService.updateRent(id, rentDetails);
            return new ResponseEntity<>(updatedRent, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminazione di un noleggio per ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRent(@PathVariable Long id) {
        rentService.deleteRent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

/*
Promemoria:

    @GetMapping("/all") --> "Endpoint"
    public ResponseEntity<List<Rent>> getAllRents() { --> Metodo che : restituisce uno status su una lista di noleggi -metodo getAllRents
        List<Rent> rents = rentService.getAllRents(); --> Assegna alla lista rents il metodo di rentService che ovviamente restituisce la lista
        return new ResponseEntity<>(rents, HttpStatus.OK); --> ritorna sia il "responso?", se "OK" + la lista in questione
    }
 */