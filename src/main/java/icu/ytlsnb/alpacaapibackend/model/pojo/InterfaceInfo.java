package icu.ytlsnb.alpacaapibackend.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import icu.ytlsnb.alpacaapibackend.exception.BusinessException;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tb_interface_info")
public class InterfaceInfo {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    private String title;

    private String description;

    private String method;

    private String url;

    @TableField("request_header")
    private String requestHeader;

    @TableField("request_params")
    private String requestParams;

    @TableField("request_body")
    private String requestBody;

    @TableField("request_example")
    private String requestExample;

    @TableField("request_header_example")
    private String requestHeaderExample;

    @TableField("request_body_example")
    private String requestBodyExample;

    @TableField("response_header")
    private String responseHeader;

    @TableField("response_body_example")
    private String responseBodyExample;

    @TableField("invoke_num")
    private Integer invokeNum;

    private Integer status;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField("is_deleted")
    @TableLogic(value = "0", delval = "1")
    private int isDeleted;

    public static void checkParams(InterfaceInfo interfaceInfo) {
        String method = interfaceInfo.getMethod();
        if (method == null || "".equals(method)) {
            throw new BusinessException(500, "请求方式不能为空...");
        }
        String title = interfaceInfo.getTitle();
        if (title == null || title.isEmpty()) {
            throw new BusinessException(500, "接口名称不能为空...");
        }
        String url = interfaceInfo.getUrl();
        if (url == null || url.isEmpty()) {
            throw new BusinessException(500, "请求地址不能为空...");
        }
        Integer status = interfaceInfo.getStatus();
        if (status == null || status < 0 || status > 1) { // 假设状态只能是0或1
            interfaceInfo.setStatus(0);
        }
        String requestExample = interfaceInfo.getRequestExample();
        if (requestExample != null && requestExample.length() > 256) {
            throw new BusinessException(500, "示例请求不能超过256字符...");
        }
    }
}
