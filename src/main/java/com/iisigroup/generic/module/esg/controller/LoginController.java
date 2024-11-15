package com.iisigroup.generic.module.esg.controller;

import com.iisigroup.generic.aop.Loggable;
import com.iisigroup.generic.dto.RespBodyDTO;
import com.iisigroup.generic.module.esg.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
public class LoginController {

    private final LoginService loginService;

//    @PostMapping("/test")
//    @Loggable
//    @Operation(description = "using test")
//    public RespBodyDTO<String> test(@Valid @RequestBody LoginDto loginDto) throws IOException {
//        return getRespBodyDTO("test", "hello " + loginService.example(loginDto.getUserName()));
//    }

    @GetMapping("/oc64")
    @Loggable
    @Operation(description = "using test")
    public RespBodyDTO<List<com.iisigroup.ocapi.entity.Roles>> find64()  {
        return getRespBodyDTO("646464", loginService.find64service());
    }

    @GetMapping("/oc67")
    @Loggable
    @Operation(description = "using test")
    public RespBodyDTO<List<com.iisigroup.generic.module.oc67.entity.Roles>> find67()  {
        return getRespBodyDTO("676767", loginService.find67service());
    }

}
