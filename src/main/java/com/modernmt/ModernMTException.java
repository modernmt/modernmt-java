package com.modernmt;

import java.io.IOException;

public class ModernMTException extends IOException {

    // keep public for backward compatibility
    public final int status;
    public final String type;

    private final Object metadata;

    public ModernMTException(int status, String type, String message, Object metadata) {
        super("[" + type + "] " + message);
        this.status = status;
        this.type = type;
        this.metadata = metadata;
    }

    public ModernMTException(int status, String type, String message) {
        this(status, type, message, null);
    }

    public int getStatusCode() {
        return status;
    }

    public String getType() {
        return type;
    }

    @SuppressWarnings("unchecked")
    public <T> T getMetadata() {
        return (T) metadata;
    }
}
