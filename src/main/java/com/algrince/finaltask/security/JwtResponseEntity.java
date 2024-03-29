package com.algrince.finaltask.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Sends response to the client with passed DTO in the body,
 * JWT-token in headers and Http status 200.
 * @param <T>   DTO of the object
 */

public class JwtResponseEntity<T> extends ResponseEntity<T> {
    public JwtResponseEntity(T body, String token) {
        super(body, buildHeaders(token), HttpStatus.OK);
    }

    private static HttpHeaders buildHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Access-Control-Expose-Headers", "Authorization");
        return headers;
    }
}
