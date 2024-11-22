package com.iisigroup.generic.dto;


import com.iisigroup.generic.constant.Constants;

import java.io.Serial;
import java.io.Serializable;

public class R<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final String SUCCESS = Constants.SUCCESS;

    public static final String FAIL = Constants.FAIL;

    private String responseCode;

    private String responseMessage;

    private T dataSet;

    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> fail() {
        return restResult(null, FAIL, null);
    }

    public static <T> R<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, FAIL, null);
    }

    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> R<T> fail(String code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> R<T> restResult(T data, String code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setResponseCode(code);
        apiResult.setResponseMessage(msg);
        apiResult.setDataSet(data);
        return apiResult;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public T getDataSet() {
        return dataSet;
    }

    public void setDataSet(T dataSet) {
        this.dataSet = dataSet;
    }

    public static <T> Boolean isError(R<T> ret) {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(R<T> ret) {
        return R.SUCCESS.equals(ret.getResponseCode());
    }
}
