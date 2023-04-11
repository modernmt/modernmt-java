package com.modernmt;

import java.io.IOException;

public class ModernMTException extends IOException {

    // keep public for backward compatibility
    public final int status;
    public final String type;

    public ModernMTException( int status, String type, String message) {
        super("[" + type + "] " + message);
        this.status = status;
        this.type = type;
    }

    public int getStatusCode() {
        return status;
    }

    public String getType() {
        return type;
    }

}
