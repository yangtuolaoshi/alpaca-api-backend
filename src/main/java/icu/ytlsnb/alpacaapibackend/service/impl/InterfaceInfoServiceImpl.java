package icu.ytlsnb.alpacaapibackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.ytlsnb.alpacaapibackend.exception.BusinessException;
import icu.ytlsnb.alpacaapibackend.mapper.InterfaceInfoMapper;
import icu.ytlsnb.alpacaapibackend.model.pojo.InterfaceInfo;
import icu.ytlsnb.alpacaapibackend.model.pojo.User;
import icu.ytlsnb.alpacaapibackend.model.request.OnlineInvokeRequest;
import icu.ytlsnb.alpacaapibackend.service.InterfaceInfoService;
import icu.ytlsnb.alpacaapiclientsdk.client.AlpacaAPIClient;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static icu.ytlsnb.alpacaapibackend.constant.ResultCodes.*;

@Service
public class InterfaceInfoServiceImpl implements InterfaceInfoService {
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Resource
    private AlpacaAPIClient alpacaAPIClient;

    @Override
    public List<InterfaceInfo> getByPage(int page, int size) {
        return interfaceInfoMapper.selectList(new Page<>(page, size), null);
    }

    @Override
    public Long getCount() {
        return interfaceInfoMapper.selectCount(null);
    }

    @Override
    public InterfaceInfo getById(Long id) {
        return interfaceInfoMapper.selectById(id);
    }

    @Override
    public Object onlineInvoke(OnlineInvokeRequest onlineInvokeRequest, HttpServletRequest request) {
        Long id = onlineInvokeRequest.getId();
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(NO_DATA_ERROR, "请求接口不存在...");
        }
        int status = interfaceInfo.getStatus();
        if (status == 0) {
            throw new BusinessException(FORBIDDEN, "接口已关闭，请稍后再试...");
        }
        User user = (User) request.getSession().getAttribute("user");
        String accessKey = user.getAccessKey();
        String secretKey = user.getSecretKey();
        // TODO 这里仍然是写死的方法名称，以后还是需要使用实际的地址来访问
        AlpacaAPIClient alpacaAPIClient = new AlpacaAPIClient(accessKey, secretKey);
        String result = alpacaAPIClient.msg1();
        // 调用次数加一
        LambdaUpdateWrapper<InterfaceInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(InterfaceInfo::getId, id);
        updateWrapper.setSql("invoke_num = invoke_num + 1");
        interfaceInfoMapper.update(updateWrapper);
        return result;
    }

    @Override
    public void update(InterfaceInfo interfaceInfo) {
        InterfaceInfo.checkParams(interfaceInfo);
        interfaceInfo.setUpdateTime(LocalDateTime.now());
        interfaceInfoMapper.updateById(interfaceInfo);
    }

    @Override
    public void insert(InterfaceInfo interfaceInfo, HttpServletRequest request) {
        InterfaceInfo.checkParams(interfaceInfo);
        User user = (User) request.getSession().getAttribute("user");
        interfaceInfo.setUserId(user.getId());
        interfaceInfo.setInvokeNum(0);
        interfaceInfo.setStatus(0);
        interfaceInfo.setCreateTime(LocalDateTime.now());
        interfaceInfoMapper.insert(interfaceInfo);
    }

    @Override
    public void deleteById(Long id) {
        interfaceInfoMapper.deleteById(id);
    }

    @Override
    public void publishInterfaceInfo(Long id) {
        // 检测接口是否存在
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(NO_DATA_ERROR, "数据不存在...");
        }
        // 判断该接口是否可以调用
        // TODO 现在使用的是固定的方法名，以后应该使用地址
        String msg = alpacaAPIClient.msg1();
        if (StringUtils.isEmpty(msg)) {
            throw new BusinessException(SERVER_ERROR, "接口不可用，请稍后再试...");
        }
        // 修改数据库的状态字段
        interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(1);
        interfaceInfo.setUpdateTime(LocalDateTime.now());
        interfaceInfoMapper.updateById(interfaceInfo);
    }

    @Override
    public void offlineInterfaceInfo(Long id) {
        // 检测接口是否存在
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(NO_DATA_ERROR, "数据不存在...");
        }
        // 修改数据库的状态字段
        interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(0);
        interfaceInfo.setUpdateTime(LocalDateTime.now());
        interfaceInfoMapper.updateById(interfaceInfo);
    }
}
