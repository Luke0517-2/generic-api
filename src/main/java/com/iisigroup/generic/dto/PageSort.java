package com.iisigroup.generic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

/**
 * 查詢時的分頁與排序條件
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageSort {

    @PositiveOrZero(message = "page must >= 0")
    private Integer page = 0;

    @Max(value = 1000, message = "maximum = 1000")
    @PositiveOrZero(message = "size must >= 0")
    private Integer size = 100;

    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "sortBy must be alphanumeric")
    private String sortBy = null;

    private boolean sortAsc = true;
}
