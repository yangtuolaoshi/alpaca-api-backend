package icu.ytlsnb.alpacaapibackend.model;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;

    private T data;

    private String msg;

    public Result() {
        this.code = 200;
        this.msg = "success";
    }

    public Result(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static<T> Result<T> ok() {
        return new Result<>(200, null, "success");
    }

    public static<T> Result<T> ok(T data) {
        return new Result<>(200, data, "success");
    }

    public static<T> Result<T> fail(Integer code, String msg) {
        return new Result<>(code, null, msg);
    }
}
