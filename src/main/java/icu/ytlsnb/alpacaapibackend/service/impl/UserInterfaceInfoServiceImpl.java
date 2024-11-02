package icu.ytlsnb.alpacaapibackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import icu.ytlsnb.alpacaapibackend.exception.BusinessException;
import icu.ytlsnb.alpacaapibackend.mapper.UserInterfaceInfoMapper;
import icu.ytlsnb.alpacaapibackend.model.pojo.UserInterfaceInfo;
import icu.ytlsnb.alpacaapibackend.service.UserInterfaceInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import static icu.ytlsnb.alpacaapibackend.constant.ResultCodes.PARAMS_ERROR;

@Service
public class UserInterfaceInfoServiceImpl implements UserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public void invokeTimeChange(Long interfaceInfoId, Long userId) {
        if (interfaceInfoId == null || interfaceInfoId <= 0 || userId == null || userId <=0) {
            throw new BusinessException(PARAMS_ERROR, "参数不能为空...");
        }
        // TODO 这里可以用锁或者分布式锁优化
        LambdaUpdateWrapper<UserInterfaceInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId)
                .eq(UserInterfaceInfo::getUserId, userId)
                .gt(UserInterfaceInfo::getLeftTime, 0);
        updateWrapper.setSql("left_time = left_time - 1, total_time = total_time + 1");
        userInterfaceInfoMapper.update(updateWrapper);
    }
}
