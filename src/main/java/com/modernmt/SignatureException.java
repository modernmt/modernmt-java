package com.modernmt;

import com.auth0.jwt.exceptions.JWTVerificationException;

public class SignatureException extends ModernMTException {

    public SignatureException(JWTVerificationException e) {
        super(0, "SignatureException", e.getMessage());
    }

}
