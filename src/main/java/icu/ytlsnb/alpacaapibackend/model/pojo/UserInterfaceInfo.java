package icu.ytlsnb.alpacaapibackend.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tb_user_interface_info")
public class UserInterfaceInfo {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("interface_info_id")
    private Long interfaceInfoId;

    @TableField("total_time")
    private Integer totalTime;

    @TableField("left_time")
    private Integer leftTime;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField("is_deleted")
    @TableLogic(value = "0", delval = "1")
    private int isDeleted;
}
