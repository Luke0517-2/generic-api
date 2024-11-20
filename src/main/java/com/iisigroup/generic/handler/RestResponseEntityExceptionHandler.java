package com.iisigroup.generic.handler;

import com.iisigroup.generic.constant.Constants;
import com.iisigroup.generic.dto.RespBodyDTO;
import com.iisigroup.generic.dto.RespBodySkeleton;
import com.iisigroup.generic.exception.ServiceException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

/**
 * ClassName:RestResponseEntityExceptionHandler
 * Package:com.iisigroup.generic.handler
 * Description:
 *
 * @Date:2024/11/15 上午 08:41
 * @Author:2208021
 */
@Slf4j
@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public RespBodyDTO handleException(Exception ex, WebRequest request) {

        // for debug
        log.error("Error occurred while processing the request: {}", request.getDescription(false), ex);

        if (ex instanceof ServiceException) {
            return handleServiceException((ServiceException) ex);
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return handleMethodArgumentNotValidException((MethodArgumentNotValidException) ex);
        }
        if (ex instanceof BindException) {
            return handleBindException((BindException) ex);
        }
        if (ex instanceof ConstraintViolationException) {
            return handleConstraintViolationException((ConstraintViolationException) ex);
        }

        return buildRespBodyDTO(ex.getClass().getName(), Constants.FAIL_CODE, ex.getMessage());
    }

    private RespBodyDTO handleServiceException(ServiceException e) {
        return buildRespBodyDTO(e.getClass().getSimpleName(), e.getCode() != null ? e.getCode() : Constants.FAIL_CODE, e.getMessage());
    }

    private RespBodyDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return buildRespBodyDTO(e.getClass().getSimpleName(), e.getStatusCode().value(), e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", ")));
    }

    private RespBodyDTO handleBindException(BindException e) {
        return buildRespBodyDTO(e.getClass().getSimpleName(), Constants.FAIL_CODE, e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", ")));
    }

    private RespBodyDTO handleConstraintViolationException(ConstraintViolationException e) {
        return buildRespBodyDTO(e.getClass().getSimpleName(), Constants.FAIL_CODE, e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", ")));
    }

    private RespBodyDTO buildRespBodyDTO(String type, Integer code, String errMsg) {
        return RespBodyDTO.builder()
                .body(RespBodySkeleton.builder()
                        .type(type)
                        .responseCode(code)
                        .responseMsg(errMsg).build())
                .build();
    }
}
