package com.iisigroup.generic.module.esg.service;

import java.io.IOException;
import java.util.List;

/**
 * ClassName:LoginService
 * Package:com.iisigroup.generic.service
 * Description:
 *
 * @Date:2024/11/14 下午 03:18
 * @Author:2208021
 */
public interface LoginService {

    String example(String input) throws IOException;

    List<com.iisigroup.ocapi.entity.Roles> find64service();
    List<com.iisigroup.generic.module.oc67.entity.Roles> find67service();

}
