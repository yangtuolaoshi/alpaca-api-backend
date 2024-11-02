package icu.ytlsnb.alpacaapibackend.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import icu.ytlsnb.alpacaapibackend.exception.BusinessException;
import icu.ytlsnb.alpacaapibackend.mapper.UserMapper;
import icu.ytlsnb.alpacaapibackend.model.pojo.User;
import icu.ytlsnb.alpacaapibackend.model.request.LoginRequest;
import icu.ytlsnb.alpacaapibackend.model.request.RegisterRequest;
import icu.ytlsnb.alpacaapibackend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static icu.ytlsnb.alpacaapibackend.constant.ResultCodes.*;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    private static final String SALT = "ytls";

    private String encipher(String basePassword) {
        basePassword += SALT;
        Digester digester = new Digester(DigestAlgorithm.MD5);
        return digester.digestHex(basePassword);
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        if (user != null) {
            throw new BusinessException(DATA_DUPLICATE_ERROR, "用户名已存在...");
        }
        String nickname = registerRequest.getNickname();
        lambdaQueryWrapper.clear();
        lambdaQueryWrapper.eq(User::getNickname, nickname);
        user = userMapper.selectOne(lambdaQueryWrapper);
        if (user != null) {
            throw new BusinessException(DATA_DUPLICATE_ERROR, "昵称重复，换一个试试...");
        }
        String inputPassword = registerRequest.getPassword();
        String repeatPassword = registerRequest.getRepeatPassword();
        if (!inputPassword.equals(repeatPassword)) {
            throw new BusinessException(PARAMS_ERROR, "两次密码输入不一致...");
        }
        String password = encipher(inputPassword);// 密码加密
        // 分配ak、sk
        String accessKey = DigestUtil.md5Hex(SALT + username + RandomUtil.randomNumbers(5));
        String secretKey = DigestUtil.md5Hex(SALT + username + RandomUtil.randomNumbers(8));
        // 插入数据
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setAccessKey(accessKey);
        user.setSecretKey(secretKey);
        user.setRole(0);
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Override
    public User login(HttpServletRequest request, LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        if (user == null) {
            throw new BusinessException(NO_DATA_ERROR, "用户不存在...");
        }
        String password = user.getPassword();
        String inputPassword = encipher(loginRequest.getPassword());
        if (!password.equals(inputPassword)) {
            throw new BusinessException(PARAMS_ERROR, "用户名或密码错误...");
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(120 * 60);
        return user;
    }
}
