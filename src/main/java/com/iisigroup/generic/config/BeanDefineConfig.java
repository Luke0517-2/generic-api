package com.iisigroup.generic.config;

import com.iisigroup.generic.utils.JWTInfoHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * ClassName:BeanDefineConfig
 * Package:com.iisigroup.generic.config
 * Description:
 *
 * @Date:2024/11/14 上午 09:47
 * @Author:2208021
 */
@EnableAsync
@EnableScheduling
@Configuration
public class BeanDefineConfig {

    @Bean
    public JWTInfoHelper JWTInfoHelper() {
        return new JWTInfoHelper();
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .build();
    }

    @Bean
    public ThreadPoolTaskExecutor asyncPoolTaskExecutor() { // application.yaml無法更改拒絕策略
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10); // 最少thread數
        taskExecutor.setMaxPoolSize(100); // 最大thread數
        taskExecutor.setQueueCapacity(50); // 等待queue大小
        taskExecutor.setKeepAliveSeconds(60); // 空閒thread存活時間
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(20);
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 當queue以滿且thread數達到max時的拒絕策略
        taskExecutor.initialize();
        return taskExecutor;
    }

}
