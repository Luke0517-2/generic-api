package com.iisigroup.generic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * ClassName:AAA
 * Package:com.iisigroup.ocapi.dto
 * Description:
 *
 * @Date:2024/3/19 上午 09:50
 * @Author:2208021
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestContent<T> {

//    @Valid
//    private PageSort meta;

    @NotNull(message = "type is required")
    private  String type;

    @Valid
    @NotNull(message = "data is required")
    private T data;

    private PageSort pageSort;
}
