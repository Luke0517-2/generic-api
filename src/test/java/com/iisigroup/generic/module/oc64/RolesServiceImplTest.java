package com.iisigroup.generic.module.oc64;

import com.iisigroup.generic.config.MyCustomContextLoader;
import com.iisigroup.generic.module.oc64.service.impl.RolesServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * ClassName:RolesServiceImplTest
 * Package:com.iisigroup.generic.module.oc64
 * Description:
 *
 * @Date:2024/11/19 上午 08:40
 * @Author:2208021
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(loader = MyCustomContextLoader.class)
public class RolesServiceImplTest {

    @Autowired
    private RolesServiceImpl rolesService;

    @Test
    void checkNotNull() {
        Assertions.assertNotNull(rolesService);
    }
}
