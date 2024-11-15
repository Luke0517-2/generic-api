package com.iisigroup.generic.module.esg.service.impl;

import com.iisigroup.generic.constant.ResponseCodeEnum;
import com.iisigroup.generic.exception.ServiceException;
import com.iisigroup.generic.module.esg.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * ClassName:LoginServiceImpl
 * Package:com.iisigroup.generic.service.impl
 * Description:
 *
 * @Date:2024/11/14 下午 03:18
 * @Author:2208021
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final com.iisigroup.generic.module.oc67.repository.RolesRepository repository7;
    private final com.iisigroup.generic.module.oc64.repository.RolesRepository repository4;

    @Override
    public String example(String input) throws IOException {
        if ("123".equals(input))
            throw new IOException("This is a simulated IOException");
        if ("456".equals(input))
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1201);

        return StringUtils.appendIfMissingIgnoreCase(input, " Luke");
    }

    @Override
    public List<com.iisigroup.ocapi.entity.Roles> find64service() {
        return repository4.findAll();
    }

    @Override
    public List<com.iisigroup.generic.module.oc67.entity.Roles> find67service() {
        return repository7.findAll();
    }
}
