package com.iisigroup.generic.module.esg.service.impl;

import com.iisigroup.generic.config.EsgPlatformProperties;
import com.iisigroup.generic.module.esg.dto.LoginInputDto;
import com.iisigroup.generic.module.esg.dto.LoginOutputDto;
import com.iisigroup.generic.module.esg.dto.LogoutInputDto;
import com.iisigroup.generic.module.esg.service.LoginService;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * ClassName:LoginServiceImpl
 * Package:com.iisigroup.generic.service.impl
 * Description:
 *
 * @Date:2024/11/14 下午 03:18
 * @Author:2208021
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final RestTemplate restTemplate;
    private final EsgPlatformProperties esgPlatformProperties;
    private final static String TOKEN_JSON_PATH = "$['access_token']";
    private final static String REFRESH_TOKEN_JSON_PATH = "$['refresh_token']";
    private final static String LOGIN_PATH = "/auth/verify";
    private final static String LOGOUT_PATH = "/auth/logout";

    @Override
    public LoginOutputDto login(LoginInputDto user) {
        log.info("Generic Api User {} login...", user.getUserName());
        log.info("ESG-Service url : {}", esgPlatformProperties.getUrl() + LOGIN_PATH );
        String responseStr = restTemplate.postForObject(esgPlatformProperties.getUrl() + LOGIN_PATH, apiRequestEntityTemplate(user), String.class);
        return wrapperJwt(responseStr);
    }

    @Override
    public void logout(LogoutInputDto logoutInputDto) {

    }

    private HttpHeaders apiRequestHeadersTemplate() {
        return new HttpHeaders() {{
            set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            set(HttpHeaders.ACCEPT, MediaType.ALL_VALUE);
        }};
    }

    private <T> HttpEntity<T> apiRequestEntityTemplate(T payload) {
        return new HttpEntity<>(payload, apiRequestHeadersTemplate());
    }

    private LoginOutputDto wrapperJwt(String responseStr) {
        DocumentContext context = JsonPath.parse(responseStr);
        return LoginOutputDto.builder()
                .accessToken(context.read(TOKEN_JSON_PATH).toString())
                .refreshToken(context.read(REFRESH_TOKEN_JSON_PATH).toString())
                .build();
    }
}
