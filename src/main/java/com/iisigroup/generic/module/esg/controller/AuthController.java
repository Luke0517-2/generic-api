package com.iisigroup.generic.module.esg.controller;

import com.iisigroup.generic.aop.Loggable;
import com.iisigroup.generic.constant.Constants;
import com.iisigroup.generic.dto.RespBodyDTO;
import com.iisigroup.generic.module.esg.dto.LoginInputDto;
import com.iisigroup.generic.module.esg.dto.LoginOutputDto;
import com.iisigroup.generic.module.esg.dto.LogoutInputDto;
import com.iisigroup.generic.module.esg.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.iisigroup.generic.dto.RespBodyDTO.getRespBodyDTO;

/**
 * ClassName:LoginController
 * Package:com.iisigroup.generic.controller
 * Description:
 *
 * @Date:2024/11/14 上午 10:24
 * @Author:2208021
 */

@Tag(name = "LoginController", description = "登入")
@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Loggable
    @Operation(description = "login")
    public RespBodyDTO<LoginOutputDto> login(@RequestBody @Valid LoginInputDto loginInputDto) {
        return getRespBodyDTO("esg", authService.login(loginInputDto));
    }

    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    @Loggable
    @Operation(description = "logout")
    public RespBodyDTO<String> logout(@RequestBody @Valid LogoutInputDto logoutInputDto) {
        authService.logout(logoutInputDto);
        return getRespBodyDTO("esg", Constants.SUCCESS_MSG);
    }
}
