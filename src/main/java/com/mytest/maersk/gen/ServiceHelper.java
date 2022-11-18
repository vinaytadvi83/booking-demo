package com.mytest.maersk.gen;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/*
 * Helper class to make service call
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
