package com.mytest.maersk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mytest.maersk.helper.ServiceHelper;
import com.mytest.maersk.model.Booking;
import com.mytest.maersk.model.ContainerType;
import com.mytest.maersk.repository.BookingRepository;
import com.mytest.maersk.service.BookingService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

import static com.mytest.maersk.result.matcher.ResponseBodyMatchers.responseBody;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = BookingController.class)
//@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookingControllerWebTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private ServiceHelper serviceHelper;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingController bookingController;

    /*@BeforeEach
    public void setup() {
        //mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }*/

    @Test
    void validationExceptionWhenContainerSizeIsNot20or40() throws Exception {
        Booking booking = new Booking(123456789L, 30, ContainerType.DRY, "London", "Dovar", 6, "2020-10-12T13:53:09Z");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(post("/api/bookings/isBookingAvailable")
                .content(ow.writeValueAsString(booking))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validationExceptionWhenOriginValueIsShortThan5Chars() throws Exception {
        Booking booking = new Booking(123456789L, 20, ContainerType.DRY, "Lon", "Dovar", 6, "2020-10-12T13:53:09Z");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(post("/api/bookings/isBookingAvailable")
                        .content(ow.writeValueAsString(booking))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validationExceptionWhenOriginValueIsLongerThan20Chars() throws Exception {
        Booking booking = new Booking(123456789L, 20, ContainerType.DRY, "London_United_Kingdon", "Dovar", 6, "2020-10-12T13:53:09Z");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(post("/api/bookings/isBookingAvailable")
                        .content(ow.writeValueAsString(booking))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validationExceptionWhenDestinationValueIsShortThan5Chars() throws Exception {
        Booking booking = new Booking(123456789L, 20, ContainerType.DRY, "London", "Dov", 6, "2020-10-12T13:53:09Z");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(post("/api/bookings/isBookingAvailable")
                        .content(ow.writeValueAsString(booking))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validationExceptionWhenDestinationValueIsLongerThan20Chars() throws Exception {
        Booking booking = new Booking(123456789L, 20, ContainerType.DRY, "London", "Dovar__United_Kingdom", 6, "2020-10-12T13:53:09Z");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(post("/api/bookings/isBookingAvailable")
                        .content(ow.writeValueAsString(booking))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validationExceptionWhenQuantityIsMoreThan100() throws Exception {
        Booking booking = new Booking(123456789L, 20, ContainerType.DRY, "London", "Dovar", 120, "2020-10-12T13:53:09Z");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(post("/api/bookings/isBookingAvailable")
                        .content(ow.writeValueAsString(booking))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validationExceptionWhenTimeStampIsInCorrect() throws Exception {
        Booking booking = new Booking(123456789L, 20, ContainerType.DRY, "London", "Dovar", 10, "2020-100-12T13:53:09Z");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(post("/api/bookings/isBookingAvailable")
                        .content(ow.writeValueAsString(booking))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void isBookingAvailableWhenAvailableSpaceZero() {
        Booking booking = new Booking(123456789L, 20, ContainerType.DRY, "London", "Dovar", 6, "2020-10-12T13:53:09Z");
        Map<String, Integer> testJSON = new HashMap<>();
        testJSON.put("availableSpace", 0);
        JSONObject jsonObject = new JSONObject(testJSON);
        //given(serviceHelper.callService("maersk-endpoints.endpoints.inquiry")).willReturn(jsonObject);
        given(bookingService.isBookingAvailable(booking)).willReturn(false);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            mockMvc.perform(post("/api/bookings/isBookingAvailable")
                    .content(ow.writeValueAsString(booking))
                    .contentType("application/json"))
                    .andExpect(responseBody()
                            .containsObjectAsJson(false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *
     */
    @Test
    void isBookingAvailableWhenAvailableSpaceNotZero() {
        Booking booking = new Booking(0L,20, ContainerType.DRY, "London", "Dovar", 6, "2020-10-12T13:53:09Z");
        Map<String, Integer> testJSON = new HashMap<>();
        testJSON.put("availableSpace", 10);
        JSONObject jsonObject = new JSONObject(testJSON);
        //given(bookingService.isBookingAvailable(booking)).willReturn(true);
        given(serviceHelper.callService("maersk-endpoints.endpoints.inquiry")).willReturn(jsonObject);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            mockMvc.perform(post("/api/bookings/isBookingAvailable")
                    .content(ow.writeValueAsString(booking))
                    .contentType("application/json"))
                    .andExpect(responseBody()
                            .containsObjectAsJson(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
