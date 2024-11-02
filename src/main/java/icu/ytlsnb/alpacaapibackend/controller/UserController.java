package icu.ytlsnb.alpacaapibackend.controller;

import icu.ytlsnb.alpacaapibackend.exception.BusinessException;
import icu.ytlsnb.alpacaapibackend.model.Result;
import icu.ytlsnb.alpacaapibackend.model.pojo.User;
import icu.ytlsnb.alpacaapibackend.model.request.LoginRequest;
import icu.ytlsnb.alpacaapibackend.model.request.RegisterRequest;
import icu.ytlsnb.alpacaapibackend.service.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import static icu.ytlsnb.alpacaapibackend.constant.ResultCodes.PARAMS_ERROR;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/reg")
    public Result<Object> register(@RequestBody RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String nickname = registerRequest.getNickname();
        String repeatPassword = registerRequest.getRepeatPassword();
        if (StringUtils.isEmpty(username)) {
            throw new BusinessException(PARAMS_ERROR, "用户名不能为空...");
        }
        if (StringUtils.isEmpty(nickname)) {
            throw new BusinessException(PARAMS_ERROR, "昵称不能为空...");
        }
        if (StringUtils.isEmpty(password)) {
            throw new BusinessException(PARAMS_ERROR, "密码不能为空...");
        }
        if (StringUtils.isEmpty(repeatPassword)) {
            throw new BusinessException(PARAMS_ERROR, "请再次输入密码...");
        }
        userService.register(registerRequest);
        return Result.ok();
    }

    @PostMapping("/login")
    public Result<User> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        if (StringUtils.isEmpty(username)) {
            throw new BusinessException(PARAMS_ERROR, "用户名不能为空...");
        }
        if (StringUtils.isEmpty(password)) {
            throw new BusinessException(PARAMS_ERROR, "密码不能为空...");
        }
        return Result.ok(userService.login(request, loginRequest));
    }
}
