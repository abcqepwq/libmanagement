package com.company.libmanagementutils.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtUtilsTest {

    @Test
    void getToken() {
        Map<String, String>  map = new HashMap<>();
        map.put("userId", "16");
        map.put("role", "Admin");
        String actual = JwtUtils.getToken(map);
        String expected = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTgwNjQyNDAsInRlc3RhIjoidGVzdGIifQ.3JrsQhf7wbCWvKkagY-Ymeirk-9Dez7vs-3Yr4qmbZk";
        assertNotEquals(actual, expected);
    }

    @Test
    void getRoleIdInToken() {
        Map<String, String>  map = new HashMap<>();
        map.put("userId", "1");
        map.put("role", "Admin");
//        String token = JwtUtils.getToken(map);
        String token  = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiIiwiZXhwIjoxNjk4MTkzOTE2LCJ1c2VySWQiOiIyNyJ9.QI6ESD9HjceSwd0vanHsgdrTQCxnc5nzVxOwl9KsXK0";
        Integer actual = JwtUtils.getUserIdInToken(token);
        Integer expected = 1;
        assertEquals(actual, expected);
    }
}