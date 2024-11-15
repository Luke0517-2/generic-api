package com.iisigroup.generic.module.oc64.service;

import com.iisigroup.ocapi.entity.Roles;

import java.util.List;

/**
 * ClassName:RoleService
 * Package:com.iisigroup.generic.module.oc64.service
 * Description:
 *
 * @Date:2024/11/15 下午 03:06
 * @Author:2208021
 */
public interface RolesService {
    List<Roles> findAll();
}
