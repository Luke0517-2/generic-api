package com.iisigroup.generic.module.oc67.service.impl;

import com.iisigroup.generic.module.oc67.entity.Roles;
import com.iisigroup.generic.module.oc67.repository.RolesRepository;
import com.iisigroup.generic.module.oc67.service.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:RoleServiceImpl
 * Package:com.iisigroup.generic.module.oc67.service.impl
 * Description:
 *
 * @Date:2024/11/15 下午 03:07
 * @Author:2208021
 */
@Service
@RequiredArgsConstructor
public class RolesServiceImpl implements RolesService {

    private final RolesRepository rolesRepository;

    @Override
    public List<Roles> findAll() {
        return rolesRepository.findAll();
    }
}
