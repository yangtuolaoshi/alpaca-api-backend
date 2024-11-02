package icu.ytlsnb.alpacaapibackend.model.request;

import lombok.Data;

/**
 * 在线调用请求参数
 */
@Data
public class OnlineInvokeRequest {
    private Long id;

    private String requestParams;
}
