package com.iisigroup.generic.module.oc64.controller;

import com.iisigroup.generic.aop.Loggable;
import com.iisigroup.generic.dto.RespBodyDTO;
import com.iisigroup.generic.module.oc64.service.RolesService;
import com.iisigroup.ocapi.entity.Roles;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.iisigroup.generic.dto.RespBodyDTO.getRespBodyDTO;

/**
 * ClassName:RoleController
 * Package:com.iisigroup.generic.module.oc64.controller
 * Description:
 *
 * @Date:2024/11/15 下午 03:46
 * @Author:2208021
 */
@Tag(name = "RolesController", description = "角色")
@Validated
@RestController
@RequestMapping("/oc64/roles")
@RequiredArgsConstructor
public class RolesController {

    private final RolesService rolesService;

    @GetMapping("/findAll")
    @Loggable
    @Operation(description = "find all roles")
    public RespBodyDTO<List<Roles>> findAll()  {
        return getRespBodyDTO("read", rolesService.findAll());
    }
}
