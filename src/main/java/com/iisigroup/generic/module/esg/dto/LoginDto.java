package com.iisigroup.generic.module.esg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:LoginDto
 * Package:com.iisigroup.generic.dto
 * Description:
 *
 * @Date:2024/11/14 下午 05:22
 * @Author:2208021
 */
@io.swagger.v3.oas.annotations.media.Schema(description = "登入物件")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginDto {
    @io.swagger.v3.oas.annotations.media.Schema(description = "使用者帳號")
    private String userName;
    @io.swagger.v3.oas.annotations.media.Schema(description = "使用者密碼")
    private String passwd;
}
