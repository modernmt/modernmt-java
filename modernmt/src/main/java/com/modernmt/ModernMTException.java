package com.modernmt;

import java.io.IOException;

public class ModernMTException extends IOException {

    public final int status;
    public final String type;

    public ModernMTException( int status, String type, String message) {
        super("[" + type + "] " + message);
        this.status = status;
        this.type = type;
    }

}
