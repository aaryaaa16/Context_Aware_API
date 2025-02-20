package com.example.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Strings {
    public static String IDENTITY_APP_ID = "482730";

    public static String HEALTHCARE_APP_ID = "915460";

    public static String TICKETING_APP_ID = "307210";

    public static <T> String getJson(T object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
