package icu.ytlsnb.alpacaapibackend.model.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;

    private String password;

    private String repeatPassword;

    private String nickname;
}
