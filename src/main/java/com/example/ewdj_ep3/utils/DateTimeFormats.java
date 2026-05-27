package com.example.ewdj_ep3.utils;

import java.time.format.DateTimeFormatter;

public final class DateTimeFormats {

    private DateTimeFormats() {
    }

    public static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}

