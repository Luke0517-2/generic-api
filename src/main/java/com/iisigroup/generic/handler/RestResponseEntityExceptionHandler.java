package com.iisigroup.generic.handler;

import com.iisigroup.generic.dto.RespBodyDTO;
import com.iisigroup.generic.dto.RespBodySkeleton;
import com.iisigroup.generic.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static com.iisigroup.generic.dto.RespBodyDTO.getErrRespBodyDTO;

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

    private final static String SERVICE_EXCEPTION_TYPE = "ServiceException";

    @ExceptionHandler({Exception.class})
    public RespBodyDTO handleException(Exception ex, WebRequest request) {

        // for debug
        log.error("Error occurred while processing the request: {}", request.getDescription(false), ex);

        if (ex instanceof ServiceException) {
            return handleServiceException((ServiceException) ex);
        }

        return getErrRespBodyDTO(ex.getClass().getName(), ex.getMessage());
    }

    private RespBodyDTO handleServiceException(ServiceException e) {
        if (e.getCode() != null) {
            return RespBodyDTO.builder()
                    .body(RespBodySkeleton.builder()
                            .type(SERVICE_EXCEPTION_TYPE)
                            .responseCode(e.getCode())
                            .responseMsg(e.getMessage()).build())
                    .build();
        } else {
            return getErrRespBodyDTO(SERVICE_EXCEPTION_TYPE, e.getMessage());
        }
    }
}
