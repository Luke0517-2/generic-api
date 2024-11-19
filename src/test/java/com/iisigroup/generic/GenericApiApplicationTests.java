package com.iisigroup.generic;

import com.iisigroup.generic.config.MyCustomContextLoader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(loader = MyCustomContextLoader.class)
class GenericApiApplicationTests {

    @Test
    void contextLoads() {
    }

}