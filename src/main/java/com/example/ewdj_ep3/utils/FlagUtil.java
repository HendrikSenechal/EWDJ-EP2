package com.example.ewdj_ep3.utils;

public class FlagUtil {

    public static String toEmoji(String countryCode) {

        countryCode = countryCode.toUpperCase();

        int firstLetter =
                Character.codePointAt(countryCode, 0)
                        - 65 + 0x1F1E6;

        int secondLetter =
                Character.codePointAt(countryCode, 1)
                        - 65 + 0x1F1E6;

        return new String(Character.toChars(firstLetter))
                + new String(Character.toChars(secondLetter));
    }
}