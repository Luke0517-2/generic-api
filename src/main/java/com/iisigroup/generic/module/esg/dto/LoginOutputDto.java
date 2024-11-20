package com.iisigroup.generic.module.esg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * ClassName:LoginOutputDto
 * Package:com.iisigroup.generic.module.esg.dto
 * Description:
 *
 * @Date:2024/11/19 下午 04:13
 * @Author:2208021
 */
@Schema(description = "登入物件")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginOutputDto {
    private String accessToken;
    private String refreshToken;
}
