package com.charles.knightonlineapi.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class TestUtils {

    public static String generateRandomString() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}
