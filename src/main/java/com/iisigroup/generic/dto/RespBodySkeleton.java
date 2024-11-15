package com.iisigroup.generic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * ClassName:AAA
 * Package:com.iisigroup.ocapi.dto
 * Description:
 *
 * @Date:2024/3/19 上午 09:50
 * @Author:2208021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespBodySkeleton<T> {
    //T : List<DTO>

    private  Integer responseCode;
    private  String responseMsg;
//    private PageSort meta;
    @NotNull
    private  String type;
    @NotNull
    private T data;

    private PageInfo pageInfo;

    private Map<String, PageInfo> pageInfoMap;
}
