package com.iisigroup.generic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查詢時的分頁與排序條件
 */

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @PositiveOrZero(message = "page must >= 0")
    private Integer first = 0;

    @Max(value = 1000, message = "maximum = 1000")
    @PositiveOrZero(message = "size must >= 0")
    private Integer rows = 100;

    @PositiveOrZero(message = "size must >= 0")
    private Long totalRecords;

    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "sortField must be alphanumeric")
    private String sortField = null;

    private Boolean sortAsc = true;
}
