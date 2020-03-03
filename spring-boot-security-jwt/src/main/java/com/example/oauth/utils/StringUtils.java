package com.example.oauth.utils;

public class StringUtils {

    public static boolean isEmpty(String value) {
        if (null == value)
            return true;
        return value.isEmpty();
    }

    public static boolean isNotEmpty(String value) {
        if (null != value)
            return true;
        return !value.isEmpty();
    }
}
