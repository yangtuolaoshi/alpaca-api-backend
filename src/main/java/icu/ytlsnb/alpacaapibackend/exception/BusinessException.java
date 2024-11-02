package icu.ytlsnb.alpacaapibackend.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    private int code;

    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
