package icu.ytlsnb.alpacaapibackend.dubbo.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import icu.ytlsnb.alpacaapibackend.constant.ResultCodes;
import icu.ytlsnb.alpacaapibackend.exception.BusinessException;
import icu.ytlsnb.alpacaapibackend.mapper.InterfaceInfoMapper;
import icu.ytlsnb.alpacaapibackend.mapper.UserMapper;
import icu.ytlsnb.alpacaapibackend.service.InterfaceInfoService;
import icu.ytlsnb.alpacaapibackend.service.UserInterfaceInfoService;
import icu.ytlsnb.alpacaapibackend.service.UserService;
import icu.ytlsnb.dubbo.InterfaceInfoDubbo;
import icu.ytlsnb.dubbo.model.pojo.InterfaceInfo;
import icu.ytlsnb.dubbo.model.pojo.User;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class InterfaceInfoDubboImpl implements InterfaceInfoDubbo {
    @Resource
    private UserMapper userMapper;

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public String getName(String name) {
        System.out.println("name: " + name);
        return "name: " + name;
    }

    @Override
    public void invokeTimeChange(Long interfaceInfoId, Long userId) {
        userInterfaceInfoService.invokeTimeChange(interfaceInfoId, userId);
    }

    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        if (StringUtils.isEmpty(path)) {
            throw new BusinessException(ResultCodes.PARAMS_ERROR, "请求地址不能为空...");
        }
        if (StringUtils.isEmpty(method)) {
            throw new BusinessException(ResultCodes.PARAMS_ERROR, "请求方式不能为空...");
        }
        LambdaQueryWrapper<InterfaceInfo> interfaceInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        interfaceInfoLambdaQueryWrapper.eq(InterfaceInfo::getUrl, path)
                .eq(InterfaceInfo::getMethod, method);
        return interfaceInfoMapper.selectOne(interfaceInfoLambdaQueryWrapper);
    }

    @Override
    public User getInvokeUser(String ak) {
        if (StringUtils.isEmpty(ak)) {
            throw new BusinessException(ResultCodes.PARAMS_ERROR, "ak不能为空...");
        }
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getAccessKey, ak);
        return userMapper.selectOne(userLambdaQueryWrapper);
    }
}
