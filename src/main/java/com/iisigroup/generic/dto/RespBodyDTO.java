package com.iisigroup.generic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.iisigroup.generic.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * ClassName:ComBodyDTO
 * Package:com.iisigroup.ocapi.dto
 * Description:
 *
 * @Date:2024/3/19 上午 09:45
 * @Author:2208021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespBodyDTO<T> {

    private Map<String, Object> header;

    private RespBodySkeleton<T> body;

    public static <T> RespBodyDTO<T> getRespBodyDTO(String type, T data) {
        return RespBodyDTO.<T>builder()
                .body(RespBodySkeleton.<T>builder()
                        .type(type)
                        .data(data)
                        .responseCode(Constants.SUCCESS_CODE)
                        .responseMsg(Constants.SUCCESS_MSG).build())
                .build();
    }

    public static <T> RespBodyDTO<T> getRespBodyDTO(String type, T data, PageInfo pageInfo) {
        return RespBodyDTO.<T>builder()
                .body(RespBodySkeleton.<T>builder()
                        .type(type)
                        .data(data)
                        .pageInfo(pageInfo)
                        .responseCode(Constants.SUCCESS_CODE)
                        .responseMsg(Constants.SUCCESS_MSG).build())
                .build();
    }

    public static RespBodyDTO getErrRespBodyDTO(String type, String errMsg) {
        return RespBodyDTO.builder()
                .body(RespBodySkeleton.builder()
                        .type(type)
                        .responseCode(Constants.FAIL_CODE)
                        .responseMsg(errMsg).build())
                .build();
    }

    public static <T> RespBodyDTO<T> getRespBodyDTO(String type, T data, PageInfo pageInfo, Map<String, PageInfo> pageInfoMap) {
        return RespBodyDTO.<T>builder()
                .body(RespBodySkeleton.<T>builder()
                        .type(type)
                        .data(data)
                        .pageInfo(pageInfo)
                        .pageInfoMap(pageInfoMap)
                        .responseCode(Constants.SUCCESS_CODE)
                        .responseMsg(Constants.SUCCESS_MSG).build())
                .build();
    }
}
