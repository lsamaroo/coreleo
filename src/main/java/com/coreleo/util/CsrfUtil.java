package com.coreleo.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public final class CsrfUtil {

    private CsrfUtil() {
        // noop
    }

    public static String generateCSRFToken(final int size) throws NoSuchAlgorithmException {
        final var random = new byte[size];
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.nextBytes(random);
        // return DatatypeConverter.printBase64Binary(random);
        return Base64.getEncoder().encodeToString(random);
        // final BASE64Encoder encoder = new BASE64Encoder();
        // return encoder.encode(random);
    }

}
