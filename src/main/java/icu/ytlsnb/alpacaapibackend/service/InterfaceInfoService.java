package icu.ytlsnb.alpacaapibackend.service;

import icu.ytlsnb.alpacaapibackend.model.pojo.InterfaceInfo;
import icu.ytlsnb.alpacaapibackend.model.request.OnlineInvokeRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface InterfaceInfoService {
    List<InterfaceInfo> getByPage(int page, int size);

    Long getCount();

    InterfaceInfo getById(Long id);

    Object onlineInvoke(OnlineInvokeRequest onlineInvokeRequest, HttpServletRequest request);

    void update(InterfaceInfo interfaceInfo);

    void insert(InterfaceInfo interfaceInfo, HttpServletRequest request);

    void deleteById(Long id);

    void publishInterfaceInfo(Long id);

    void offlineInterfaceInfo(Long id);
}
