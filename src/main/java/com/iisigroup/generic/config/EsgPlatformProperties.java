package com.iisigroup.generic.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "esg-platform", ignoreInvalidFields = true)
@Getter
@Setter
public class EsgPlatformProperties {
    private String url;
}
