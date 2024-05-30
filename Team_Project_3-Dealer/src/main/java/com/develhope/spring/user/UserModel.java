package com.develhope.spring.user;

import java.util.HashSet;
import java.util.Set;

import com.develhope.spring.role.Role;
import com.develhope.spring.user.userdto.UserRequest;
import com.develhope.spring.user.userdto.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<Role> role = new HashSet<>();

    public static UserModel mapEntityToModel(Users users) {
        return new UserModel(
                users.getId(),
                users.getFirstName(),
                users.getLastName(),
                users.getEmail(),
                users.getPassword(),
                users.getRole());
    }

    public static Users mapModelToEntity(UserModel userModel) {
        return new Users(
                userModel.getId(),
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getEmail(),
                userModel.getPassword(),
                userModel.getRole());
    }

    public static UserResponse mapModelToResponse(UserModel userModel) {
        return new UserResponse(
                userModel.getId(),
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getEmail(),
                userModel.getPassword(),
                userModel.getRole());
    }

    public static UserModel mapRequestToModel(UserRequest userRequest) {
        return new UserModel(
                null,
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getEmail(),
                userRequest.getPassword(),
                userRequest.getRole());
    }

}

