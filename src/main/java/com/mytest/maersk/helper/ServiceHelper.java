package com.mytest.maersk.helper;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/*
 * Helper class to prepare and make external service call
 * */
@Component
public class ServiceHelper {

    @Autowired
    private Environment env;

    /*
     * Fetch required endpoint uri from application properties
     * */
    public Object callService (String endpoint) {
        String uri = env.getProperty(endpoint);
        RestTemplate restTemplate = new RestTemplate();
        return  restTemplate.getForObject(uri, JSONObject.class);
    }
}
