package com.iisigroup.generic;

import com.iisigroup.generic.handler.CustomBeanNameGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.iisigroup")
public class GenericApiApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(GenericApiApplication.class)
                .beanNameGenerator(new CustomBeanNameGenerator())
                .run(args);
    }
}
