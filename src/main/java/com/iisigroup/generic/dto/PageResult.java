package com.iisigroup.generic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 回傳的頁碼相關資訊
 */
@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResult {

    @PositiveOrZero(message = "page must >= 0")
    private Integer page ;

    @Max(value = 1000, message = "maximum = 1000")
    @PositiveOrZero(message = "size must >= 0")
    private Integer size;

    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "sortBy must be alphanumeric")
    private String sortBy ;

    private boolean sortAsc ;

    @PositiveOrZero(message = "totalPage must >= 0")
    private Integer totalPage;

    @PositiveOrZero(message = "totalSize must >= 0")
    private Long totalSize;
}
