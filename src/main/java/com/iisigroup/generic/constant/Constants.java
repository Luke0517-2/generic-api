package com.iisigroup.generic.constant;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String UTF8 = "UTF-8";
    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";
    public static final String SUCCESS = "000000";
    public static final String SUCCESS_MSG = "Success";
    public static final String FAIL = "2000000";
    public static final String LOGIN_SUCCESS_STATUS = "0";
    public static final String LOGIN_FAIL_STATUS = "1";
    public static final String LOGIN_SUCCESS = "Success";
    public static final String LOGOUT = "Logout";
    public static final String REGISTER = "Register";
    public static final String LOGIN_FAIL = "Error";
    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;
    public static final MathContext mathContext = new MathContext(10, RoundingMode.HALF_UP);
}