package com.iisigroup.generic;

import com.iisigroup.generic.handler.CustomBeanNameGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class GenericApiApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(GenericApiApplication.class)
                .beanNameGenerator(new CustomBeanNameGenerator())
                .run(args);
    }
}
