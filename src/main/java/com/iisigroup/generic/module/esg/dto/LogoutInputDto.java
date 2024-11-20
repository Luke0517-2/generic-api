package com.iisigroup.generic.module.esg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:LogoutInputDto
 * Package:com.iisigroup.esg.domain.platform.login
 * Description:
 *
 * @Date:2024/6/17 下午 04:59
 * @Author:2208021
 */
@io.swagger.v3.oas.annotations.media.Schema(description ="登出物件")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogoutInputDto {

    @NotNull(message = "refreshToken is required")
    @io.swagger.v3.oas.annotations.media.Schema(description ="refresh token")
    private String refreshToken;
}
