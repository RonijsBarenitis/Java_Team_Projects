package com.develhope.spring.car;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Vehicles")
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long vehicleId;
    private String brand; // Marca
    private String model; // Modello
    private String color; // Colore
    private String gearboxType; // Tipo Cambio
    private String fuelType; // Alimentazione
    private String accessories; // Eventuali accessori a corredo

    private Integer displacement; // Cilindrata
    private Integer power; // Potenza
    private Integer yearOfRegistration; // Anno immatricolazione

    private Double price; // Prezzo
    private Double discount; // Eventuale sconto sul prezzo di listino

    private Boolean isNew; // Flag che identifica se il veicolo è nuovo o usato

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    private VehicleStatus isAvailable; // Flag che identifica se il veicolo è ordinabile, acquistabile o non più
                                       // disponibile

}
