package com.iisigroup.generic.dto;

import com.iisigroup.generic.constant.Constants;
import com.iisigroup.generic.module.oc64.dto.activity.ActivityItemImportErrDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * for when excel import , it needs more message to show
 * @param <T>
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
public class R2<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final String SUCCESS = Constants.SUCCESS;

    public static final String FAIL = Constants.FAIL;

    private String responseCode;

    private List<ActivityItemImportErrDTO> responseMessage;

    private T dataSet;

    public R2() {

    }

    public static <T> R2<T> ok() {
        return restResult(null, SUCCESS, null);
    }

    public static <T> R2<T> ok(T data) {
        return restResult(data, SUCCESS, null);
    }

    public static <T> R2<T> ok(T data, List<ActivityItemImportErrDTO> msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R2<T> fail() {
        return restResult(null, FAIL, null);
    }

    public static <T> R2<T> fail(List<ActivityItemImportErrDTO> msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> R2<T> fail(T data) {
        return restResult(data, FAIL, null);
    }

    public static <T> R2<T> fail(T data, List<ActivityItemImportErrDTO> msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> R2<T> fail(String code, List<ActivityItemImportErrDTO> msg) {
        return restResult(null, code, msg);
    }

    private static <T> R2<T> restResult(T data, String code, List<ActivityItemImportErrDTO> msg) {
        R2<T> apiResult = new R2<>();
        apiResult.setResponseCode(code);
        apiResult.setResponseMessage(msg);
        apiResult.setDataSet(data);
        return apiResult;
    }

    public static <T> Boolean isError(R2<T> ret) {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(R2<T> ret) {
        return R.SUCCESS.equals(ret.getResponseCode());
    }



}
