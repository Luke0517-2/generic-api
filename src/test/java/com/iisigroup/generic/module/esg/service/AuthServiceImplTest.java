package com.iisigroup.generic.module.esg.service;

import com.iisigroup.generic.config.MyCustomContextLoader;
import com.iisigroup.generic.module.esg.dto.LoginInputDto;
import com.iisigroup.generic.module.esg.dto.LoginOutputDto;
import com.iisigroup.generic.module.esg.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * ClassName:LoginServiceImplTest
 * Package:com.iisigroup.generic.module.esg.service
 * Description:
 *
 * @Date:2024/11/19 上午 11:01
 * @Author:2208021
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(loader = MyCustomContextLoader.class)
public class AuthServiceImplTest {

    @Autowired
    private AuthServiceImpl loginService;

    @Test
    void testSendApi() {
        LoginInputDto jmtest015 = LoginInputDto.builder().userName("jmtest087").passwd("iis!ESG064").build();
        LoginOutputDto jwt = loginService.login(jmtest015);
        Assertions.assertNotNull(jwt.getAccessToken());
        Assertions.assertNotNull(jwt.getRefreshToken());
    }

}
