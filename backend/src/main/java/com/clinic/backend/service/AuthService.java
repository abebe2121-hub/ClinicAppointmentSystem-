package com.clinic.backend.service;

import com.clinic.backend.dto.request.LoginRequest;
import com.clinic.backend.dto.request.RegisterRequest;
import com.clinic.backend.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    LoginResponse register(RegisterRequest request);
}