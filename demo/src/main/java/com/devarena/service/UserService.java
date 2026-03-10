package com.devarena.service;

import com.devarena.dto.request.CreateUserRequest;
import com.devarena.dto.request.UpdateUserRequest;
import com.devarena.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);
}
