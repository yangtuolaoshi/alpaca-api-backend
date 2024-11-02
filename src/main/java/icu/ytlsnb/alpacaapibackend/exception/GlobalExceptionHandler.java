package icu.ytlsnb.alpacaapibackend.exception;

import icu.ytlsnb.alpacaapibackend.model.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static icu.ytlsnb.alpacaapibackend.constant.ResultCodes.SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result<Object> doException(Exception e) {
        e.printStackTrace();
        return Result.fail(SERVER_ERROR, "系统开小差了，请稍后再试...");
    }

    @ExceptionHandler(BusinessException.class)
    public Result<Object> doBusinessException(BusinessException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }
}
