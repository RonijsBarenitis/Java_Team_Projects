package com.develhope.spring.user.userdto;

import java.util.HashSet;
import java.util.Set;

import com.develhope.spring.role.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<Role> role = new HashSet<>();
}
