package com.iisigroup.generic;

import com.iisigroup.generic.config.MyCustomContextLoader;
import com.iisigroup.generic.module.oc64.repository.RolesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(loader = MyCustomContextLoader.class)
class GenericApiApplicationTests {

    @Autowired
    private RolesRepository repository;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(repository);
    }

}