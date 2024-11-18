package com.iisigroup.generic.handler;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

/**
 * ClassName:CustomBeanNameGenerator
 * Package:com.iisigroup.generic.handler
 * Description:
 *
 * @Date:2024/11/18 下午 04:35
 * @Author:2208021
 */
@NoArgsConstructor
public class CustomBeanNameGenerator implements BeanNameGenerator {
    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return definition.getBeanClassName();
    }
}
