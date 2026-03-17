package com.devarena.service;

import com.devarena.dto.request.CreateUserRequest;
import com.devarena.dto.request.UpdateUserRequest;
import com.devarena.dto.response.UserResponse;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse getUserById(Long id);

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);
}
