package com.yndw.dvp.xtgl.auth.utils;

import java.util.UUID;

public class GenerateUtils {
    public static String generate12DigitId() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString().replace("-", "");
        return uuidString.substring(0, 12);
    }
}
