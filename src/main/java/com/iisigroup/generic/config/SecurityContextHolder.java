package com.iisigroup.generic.config;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.iisigroup.generic.constant.SecurityConstants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 後端基於JWT來識別使用者身份,當request進來時會在TokenFilter解析JWT並存入SecurityContextHolder
 * 這樣在後續的程式中(Service)就可以透過SecurityContextHolder來取得使用者資訊
 */
public class SecurityContextHolder {
    private static final TransmittableThreadLocal<Map<String, Object>> THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static Map<String, Object> getLocalMap() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new ConcurrentHashMap<String, Object>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    public static void setLocalMap(Map<String, Object> threadLocalMap) {
        THREAD_LOCAL.set(threadLocalMap);
    }

    public static void set(String key, Object value) {
        if (key.isEmpty() || value == null)
            return;
        Map<String, Object> map = getLocalMap();
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = getLocalMap();
        return map.get(key);
    }

    public static String getUserId() {
        return (String) get(SecurityConstants.USER_ID);
    }

    public static void setUserId(String userId) {
        set(SecurityConstants.USER_ID, userId);
    }

    public static String getCompanyId() {
        return (String) get(SecurityConstants.COMPANY_ID);
    }

    public static void setCompanyId(String companyId) {
        set(SecurityConstants.COMPANY_ID, companyId);
    }

    public static String getToken() {
        return (String) get(SecurityConstants.JWT);
    }

    public static void setToken(String token) {
        set(SecurityConstants.JWT, token);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

    public static String getCompanyIdByJwt() {
        String preferredUsername = getUserId();
        int index = preferredUsername.indexOf('#');
        if (index == -1) {
            return preferredUsername; // 如果没有#，返回原字串
        }
        return preferredUsername.substring(0, index);
    }

    public static String getUsernameByJwt() {
        String preferredUsername = getUserId();
        int index = preferredUsername.indexOf('#');
        if (index == -1) {
            return preferredUsername; // 如果没有#，返回原字串
        }
        return preferredUsername.substring(index + 1); // 返回#之后的部分
    }
}
