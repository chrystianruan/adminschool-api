package com.api.adminschool.utils;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class ResponseUtils {
    public Map<String, String> getResponseMessage(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("response", message);
        return response;
    }
}
