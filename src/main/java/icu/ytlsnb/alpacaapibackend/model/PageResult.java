package icu.ytlsnb.alpacaapibackend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageResult<T> extends Result<T> {
    private Integer page;

    private Integer size;

    private Long total;

    public PageResult() {
    }

    public PageResult(Integer page, Integer size, Long total) {
        super();
        this.page = page;
        this.size = size;
        this.total = total;
    }

    public PageResult(Integer page, Integer size, T data, Long total) {
        super();
        this.page = page;
        this.size = size;
        this.setData(data);
        this.total = total;
    }
}
