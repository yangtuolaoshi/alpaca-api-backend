package icu.ytlsnb.alpacaapibackend.service;

public interface UserInterfaceInfoService {
    /**
     * 调用次数增加
     * @param interfaceInfoId 接口ID
     * @param userId 用户ID
     */
    void invokeTimeChange(Long interfaceInfoId, Long userId);
}
