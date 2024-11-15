package com.iisigroup.generic.exception;


import com.iisigroup.generic.constant.ResponseCodeEnum;
import lombok.Getter;

import java.io.Serial;

/**
 * 通用異常
 * 若無合適的異常類型, 則使用此類型, 且可自行定義錯誤碼
 */
public final class ServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private Integer code;

    private String message;

    @Getter
    private String detailMessage;

    public ServiceException() {
    }

    public ServiceException(ResponseCodeEnum responseCodeEnum) {
        this.code = responseCodeEnum.getCode();
        this.message = responseCodeEnum.getDefaultMessage();
    }

    public ServiceException(ResponseCodeEnum responseCodeEnum, String extraMessage) {
        this.code = responseCodeEnum.getCode();
        this.message = responseCodeEnum.getDefaultMessage() + " : " + extraMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ServiceException setMessage(String message) {
        this.message = message;
        return this;
    }

    public ServiceException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }
}
