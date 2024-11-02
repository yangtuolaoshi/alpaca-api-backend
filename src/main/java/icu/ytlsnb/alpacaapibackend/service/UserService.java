package icu.ytlsnb.alpacaapibackend.service;

import icu.ytlsnb.alpacaapibackend.model.pojo.User;
import icu.ytlsnb.alpacaapibackend.model.request.LoginRequest;
import icu.ytlsnb.alpacaapibackend.model.request.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    void register(RegisterRequest registerRequest);

    User login(HttpServletRequest request, LoginRequest loginRequest);
}
