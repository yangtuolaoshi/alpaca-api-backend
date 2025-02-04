package icu.ytlsnb.alpacaapibackend.service;

import icu.ytlsnb.alpacaapibackend.model.request.LoginRequest;
import icu.ytlsnb.alpacaapibackend.model.request.RegisterRequest;
import icu.ytlsnb.dubbo.model.pojo.User;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    void register(RegisterRequest registerRequest);

    User login(HttpServletRequest request, LoginRequest loginRequest);
}
