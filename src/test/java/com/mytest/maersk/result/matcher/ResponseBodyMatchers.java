package com.mytest.maersk.result.matcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseBodyMatchers {
    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> ResultMatcher containsObjectAsJson(
            boolean expectedResult) {
        return mvcResult -> {
            String json = mvcResult.getResponse().getContentAsString();
            JSONObject jsonObject = new JSONObject(json);
            boolean available = (Boolean)jsonObject.get("available");
            assertThat(available).isEqualTo(expectedResult);
        };
    }

    public static ResponseBodyMatchers responseBody(){
        return new ResponseBodyMatchers();
    }

}