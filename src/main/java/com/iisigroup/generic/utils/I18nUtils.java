package com.iisigroup.generic.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Slf4j
public class I18nUtils {

    /**
     * 從resources中取得對應的值
     *
     * @param key    the key
     * @param locale the locale
     * @return the i18n result
     */
    public static String getI18n(String key, String locale) {

        Locale localeTW = new Locale("zh", "TW");
        Locale localeCN = new Locale("zh", "CN");
        Locale localeEn = new Locale("en", "US");

        ResourceBundle resUS =  ResourceBundle.getBundle("content", localeEn);
        ResourceBundle resTW =  ResourceBundle.getBundle("content", localeTW);
        ResourceBundle resCN =  ResourceBundle.getBundle("content", localeCN);

        return switch (locale) {
            case "CN", "zh-CN" -> getStringSafely(resCN, key);
            case "US", "en-US" -> getStringSafely(resUS, key);
            default            -> getStringSafely(resTW, key);  //TW
        };
    }

    /**
     * 避免找不到key時的錯誤，直接回傳原本的key
     *
     * @param bundle the bundle
     * @param key    the key
     * @return the string safely
     */
    private static String getStringSafely(ResourceBundle bundle, String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            log.info("Key not found, use origin input {} " , key);
            return key;
        }
    }

}
