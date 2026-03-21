package com.devarena.User.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.devarena.User.dto.request.CreateUserRequest;
import com.devarena.User.dto.request.UpdateUserRequest;
import com.devarena.User.dto.response.UserResponse;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse getUserById(Long id);

    UserResponse getUserByUsername(String username);

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);
}
