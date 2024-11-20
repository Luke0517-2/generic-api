package com.iisigroup.generic.module.esg.service;

import com.iisigroup.generic.module.esg.dto.LoginInputDto;
import com.iisigroup.generic.module.esg.dto.LoginOutputDto;
import com.iisigroup.generic.module.esg.dto.LogoutInputDto;

/**
 * ClassName:LoginService
 * Package:com.iisigroup.generic.service
 * Description:
 *
 * @Date:2024/11/14 下午 03:18
 * @Author:2208021
 */
public interface LoginService {

    LoginOutputDto login(LoginInputDto user);
    void logout(LogoutInputDto logoutInputDto);
}
