package com.devarena.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {

    private String bio;

    private String companyName;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
