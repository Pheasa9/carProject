package com.sa.leanning.CarProject.config.jwt;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

public class JwtUtil {

    private static final String SECRET =
            "jdhshdjwlBCYWd22jdhdhdhhhdhJSDVCSALhhd";

    public static final SecretKey KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    static {
        System.out.println("JWT KEY HASH (UTIL): " + KEY.hashCode());
    }
}
