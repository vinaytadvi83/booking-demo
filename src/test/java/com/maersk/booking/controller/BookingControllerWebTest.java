package com.maersk.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.maersk.booking.result.matcher.ResponseBodyMatchers;
import com.maersk.booking.helper.ServiceHelper;
import com.maersk.booking.model.Booking;
import com.maersk.booking.model.ContainerType;
import com.maersk.booking.repository.BookingRepository;
import com.maersk.booking.service.BookingService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    /*
     * Container size validation (not 20 or 40)
     */
    @Test
    void validationExceptionWhenContainerSizeIsNot20or40() throws Exception {
        Booking booking = new Booking(123456789L, 30, ContainerType.DRY, "London", "Dovar", 6, "2020-10-12T13:53:09Z");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(post("/api/bookings/isBookingAvailable")
                .content(ow.writeValueAsString(booking))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    /*
     * Origin values less than 5 chars
     */
    @Test
    void validationExceptionWhenOriginValueIsShortThan5Chars() throws Exception {
        Booking booking = new Booking(123456789L, 20, ContainerType.DRY, "Lon", "Dovar", 6, "2020-10-12T13:53:09Z");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(post("/api/bookings/isBookingAvailable")
                        .content(ow.writeValueAsString(booking))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    /*
     * Origin values more than 20 chars
     */
    @Test
    void validationExceptionWhenOriginValueIsLongerThan20Chars() throws Exception {
        Booking booking = new Booking(123456789L, 20, ContainerType.DRY, "London_United_Kingdon", "Dovar", 6, "2020-10-12T13:53:09Z");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(post("/api/bookings/isBookingAvailable")
                        .content(ow.writeValueAsString(booking))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    /*
     * Destination values less than 5 chars
     */
    @Test
    void validationExceptionWhenDestinationValueIsShortThan5Chars() throws Exception {
        Booking booking = new Booking(123456789L, 20, ContainerType.DRY, "London", "Dov", 6, "2020-10-12T13:53:09Z");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(post("/api/bookings/isBookingAvailable")
                        .content(ow.writeValueAsString(booking))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    /*
     * Destination values more than 20 chars
     */
    @Test
    void validationExceptionWhenDestinationValueIsLongerThan20Chars() throws Exception {
        Booking booking = new Booking(123456789L, 20, ContainerType.DRY, "London", "Dovar__United_Kingdom", 6, "2020-10-12T13:53:09Z");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(post("/api/bookings/isBookingAvailable")
                        .content(ow.writeValueAsString(booking))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    /*
     * Quantity validation when more than 100
     */
    @Test
    void validationExceptionWhenQuantityIsMoreThan100() throws Exception {
        Booking booking = new Booking(123456789L, 20, ContainerType.DRY, "London", "Dovar", 120, "2020-10-12T13:53:09Z");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(post("/api/bookings/isBookingAvailable")
                        .content(ow.writeValueAsString(booking))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    /*
     * Time stamp validation test
     */
    @Test
    void validationExceptionWhenTimeStampIsInCorrect() throws Exception {
        Booking booking = new Booking(123456789L, 20, ContainerType.DRY, "London", "Dovar", 10, "2020-100-12T13:53:09Z");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(post("/api/bookings/isBookingAvailable")
                        .content(ow.writeValueAsString(booking))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    /*
     * Is booking available when "https://maersk.com/api/bookings/checkAvailable" responds with
     * availability is 0 or not enough
     */
    @Test
    void isBookingAvailableWhenAvailableSpaceZero() {
        Booking booking = new Booking(123456789L, 20, ContainerType.DRY, "London", "Dovar", 6, "2020-10-12T13:53:09Z");
        Map<String, Integer> testJSON = new HashMap<>();
        testJSON.put("availableSpace", 0);
        JSONObject jsonObject = new JSONObject(testJSON);
        given(serviceHelper.callService("maersk-endpoints.endpoints.inquiry")).willReturn(jsonObject);
        //given(bookingService.isBookingAvailable(booking)).willReturn(false);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            mockMvc.perform(post("/api/bookings/isBookingAvailable")
                    .content(ow.writeValueAsString(booking))
                    .contentType("application/json"))
                    .andExpect(ResponseBodyMatchers.responseBody()
                            .containsObjectAsJson(false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Is booking available when "https://maersk.com/api/bookings/checkAvailable" responds with
     * availability more than required
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
                    .andExpect(ResponseBodyMatchers.responseBody()
                            .containsObjectAsJson(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
