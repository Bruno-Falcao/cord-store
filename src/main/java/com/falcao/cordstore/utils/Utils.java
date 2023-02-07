package com.falcao.cordstore.utils;

import java.time.format.DateTimeFormatter;

public class Utils {

    public static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    }
}
