package com.develhope.spring.configurations.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.develhope.spring.car.Vehicle;
import com.develhope.spring.user.Users;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRes {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String name;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<String> role = new HashSet<>();
    private List<Vehicle> vehicles;
    private Users ourUsers;
}
