package com.iisigroup.generic.constant;

import lombok.Getter;

@Getter
public enum ResponseCodeEnum {
    RESPONSE_CODE_200(200, "Success"),

    // http error
    ERROR_CODE_400(400, "Bad Request"),
    ERROR_CODE_401(401, "Unauthorized"),
    ERROR_CODE_403(403, "Forbidden"),
    ERROR_CODE_404(404, "Not Found"),
    ERROR_CODE_405(405, "Method Not Allowed"),
    ERROR_CODE_406(406, "Not Acceptable"),
    ERROR_CODE_408(408, "Request Timeout"),
    ERROR_CODE_409(409, "Conflict"),
    ERROR_CODE_410(410, "Gone"),
    ERROR_CODE_411(411, "Length Required"),
    ERROR_CODE_500(500, "Internal Server Error"),

    // request error
    ERROR_CODE_1001(1001, "Missing Data Body."),
    ERROR_CODE_1002(1002, "Missing Data PageInfo."),
    ERROR_CODE_1003(1003, "Unexpected type "),
    // data error
    ERROR_CODE_1101(1101, "Resource not found"),
    ERROR_CODE_1102(1102, "Item already exists"),
    ERROR_CODE_1103(1103, "Item is not allowed to modify cause by"),
    ERROR_CODE_1104(1104, "Item is not allowed to delete cause by"),
    ERROR_CODE_1105(1105, "Enum not found"),

    ERROR_CODE_1106(1106, "The relative item is soft deleted"),
    ERROR_CODE_1107(1107, "Item is not allowed to create cause by"),
    ERROR_CODE_1108(1108, "Data Anomaly."),


    // other error
    ERROR_CODE_1201(1201, "JSON parse error."),
    ERROR_CODE_1202(1202, "Method is not completed."),
    ERROR_CODE_1203(1203, "Compute fail because denominator cannot be zero."),


    // Encrypt and Decrypt error
    ERROR_CODE_1211(1211, "Encrypt fail."),
    ERROR_CODE_1212(1212, "Decrypt fail."),

    ERROR_CODE_2001(2001, "Miss license. "),
    ERROR_CODE_2011(2011, "The module is not allowed for license : "),
    ERROR_CODE_2012(2012, "The targetName is not exist : "),

    // esg-platform error
    ERROR_CODE_1301(1301, "ESG platform Unavailable"),
    ERROR_CODE_1302(1302, "ESG platform Internal Server Error"),

    //excel error
    ERROR_CODE_1401(1401,"Mismatch type"),
    ERROR_CODE_1402(1402,"Missing field"),


    ERROR_CODE_9999(9999, "Unknown Error")

    ;
    private final int code;
    private final String defaultMessage;

    ResponseCodeEnum(int code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

}
