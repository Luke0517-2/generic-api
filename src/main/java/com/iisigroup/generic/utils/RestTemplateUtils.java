package com.iisigroup.generic.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.iisigroup.generic.constant.ResponseCodeEnum;
import com.iisigroup.generic.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static com.iisigroup.generic.constant.Constants.SUCCESS;
import static org.springframework.http.HttpMethod.*;

/**
 * ClassName:RestTemplateUtils
 * Package:com.iisigroup.ocapi.utils
 * Description:
 *
 * @Date:2024/9/16 上午 11:32
 * @Author:2208021
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RestTemplateUtils {

    private final RestTemplate restTemplate;
    private final JWTInfoHelper jwtInfoHelper;

    public HttpHeaders apiRequestHeadersTemplate() {
        return new HttpHeaders() {{
            set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            set(HttpHeaders.ACCEPT, MediaType.ALL_VALUE);
            set(HttpHeaders.AUTHORIZATION, jwtInfoHelper.showToken());
        }};
    }

    public void validateResponse(ResponseEntity<String> response) {
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1301);
        }
        JsonNode jsonNode;
        try {
            jsonNode = JsonUtils.getMapper().readTree(response.getBody());
        } catch (JsonProcessingException e) {
            log.error("validateResponse JsonProcessingException occurred. Original string: {}", e.getOriginalMessage(), e);
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1201);
        }
        if (!SUCCESS.equals(jsonNode.get("responseCode").asText())) {
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1302);
        }
    }

    public HttpEntity<String> apiRequestEntityTemplate(String requestJson) {
        return new HttpEntity<>(requestJson, apiRequestHeadersTemplate());
    }

    /**
     * 將request包裝成json
     *
     * @param request
     * @return
     */
    public String wrapperRequest(Object request) {
        try {
            return JsonUtils.bean2Json(request);
        } catch (JsonProcessingException e) {
            log.error("wrapperRequest JsonProcessingException occurred. Original string: {}", e.getOriginalMessage(), e);
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1201);
        }
    }

    /**
     * 將esg-api回傳轉成Map
     *
     * @param response
     * @return
     */
    public Map unwrapResponse(ResponseEntity<String> response) {
        try {
            return JsonUtils.json2Bean(response.getBody(), Map.class);
        } catch (JsonProcessingException e) {
            log.error("unwrapResponse JsonProcessingException occurred. Original string: {}", e.getOriginalMessage(), e);
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1201);
        }
    }

    /**
     * 用來添加GET query path
     *
     * @param uriComponentsBuilder
     * @param values
     */
    public void addNotNullQueryParam(UriComponentsBuilder uriComponentsBuilder, Object... values) {
        if (CollectionUtils.isEmpty(Arrays.asList(values))) {
            return;
        }
        Arrays.stream(values)
                .filter(Objects::nonNull)
                .map(value -> JsonUtils.getMapper().convertValue(value, new TypeReference<Map<String, Object>>() {
                }))
                .flatMap(queryParams -> queryParams.entrySet().stream())
                .filter(entry -> ObjectUtils.isNotEmpty(entry.getValue()))
                .forEach(entry -> uriComponentsBuilder.queryParam(entry.getKey(), entry.getValue()));
    }


    public <T> T getApi(UriComponentsBuilder uriComponentsBuilder, TypeReference<T> typeReference) {
        return processApi(uriComponentsBuilder, GET, typeReference, null);
    }

    public <T> T deleteApi(UriComponentsBuilder uriComponentsBuilder, Object requestEntity, TypeReference<T> typeReference) {
        return Objects.isNull(requestEntity) ? processApi(uriComponentsBuilder, DELETE, typeReference, null) : processApi(uriComponentsBuilder, DELETE, typeReference, apiRequestEntityTemplate(wrapperRequest(requestEntity)));
    }

    public <T> T postApi(UriComponentsBuilder uriComponentsBuilder, Object requestEntity, TypeReference<T> typeReference) {
        return processApi(uriComponentsBuilder, POST, typeReference, apiRequestEntityTemplate(wrapperRequest(requestEntity)));
    }

    public <T> T putApi(UriComponentsBuilder uriComponentsBuilder, Object requestEntity, TypeReference<T> typeReference) {
        return processApi(uriComponentsBuilder, PUT, typeReference, apiRequestEntityTemplate(wrapperRequest(requestEntity)));
    }

    public <T> T processApi(UriComponentsBuilder uriComponentsBuilder, HttpMethod method, TypeReference<T> typeReference, HttpEntity<String> entity) {
        if (!method.equals(GET) && !method.equals(DELETE) && Objects.isNull(entity)) {
            throw new IllegalArgumentException("Request body is required for method: " + method);
        }
        try {
            ResponseEntity<String> response = switch (method.toString()) {
                case "GET" ->
                        restTemplate.exchange(uriComponentsBuilder.toUriString(), method, new HttpEntity<>(apiRequestHeadersTemplate()), String.class);
                case "POST", "PUT" ->
                        restTemplate.exchange(uriComponentsBuilder.toUriString(), method, entity, String.class);
                case "DELETE" ->
                        Objects.isNull(entity) ? restTemplate.exchange(uriComponentsBuilder.toUriString(), method, new HttpEntity<>(apiRequestHeadersTemplate()), String.class) : restTemplate.exchange(uriComponentsBuilder.toUriString(), method, entity, String.class);
                default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
            };
            validateResponse(response);
            return JsonUtils.getMapper().convertValue(unwrapResponse(response).get("dataSet"), typeReference);
        } catch (RestClientException e) {
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1301);
        }
    }
}
