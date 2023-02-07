package com.falcao.cordstore.execeptions;

import java.time.DateTimeException;

public class ExpirationDateException extends DateTimeException {
    public ExpirationDateException(String message) {
        super(message);
    }

}
