package com.iisigroup.generic.config;

import com.iisigroup.generic.GenericApiApplication;
import com.iisigroup.generic.handler.CustomBeanNameGenerator;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextLoader;

/**
 * ClassName:MyCustomContextLoader
 * Package:com.iisigroup.generic.config
 * Description:
 *
 * @Date:2024/11/18 下午 04:20
 * @Author:2208021
 */
public class MyCustomContextLoader implements ContextLoader {
    @Override
    public String[] processLocations(Class<?> clazz, String... locations) {
        return new String[0];
    }

    @Override
    public ApplicationContext loadContext(String... locations) {
        return new SpringApplicationBuilder(GenericApiApplication.class)
                .sources()
                .beanNameGenerator(new CustomBeanNameGenerator())
                .run();
    }

}
