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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static com.mytest.maersk.result.matcher.ResponseBodyMatchers.responseBody;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BookingControllerWebTest {
    //@Autowired
    private MockMvc mockMvc;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ServiceHelper serviceHelper;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    public void setup() {
        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        // MockMvc standalone approach
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    void isBookingAvailableWhenAvailableSpaceZero() {
        Booking booking = new Booking(123456789L, 20, ContainerType.DRY, "London", "Dovar", 6, "2020-10-12T13:53:09Z");
        Map<String, Integer> testJSON = new HashMap<>();
        testJSON.put("availableSpace", 0);
        JSONObject jsonObject = new JSONObject(testJSON);
        given(serviceHelper.callService("maersk-endpoints.endpoints.inquiry")).willReturn(jsonObject);
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

    @Test
    void isBookingAvailableWhenAvailableSpaceNotZero() {
        Booking booking = new Booking(123456789L,20, ContainerType.DRY, "London", "Dovar", 6, "2020-10-12T13:53:09Z");
        //Map<String, Integer> testJSON = new HashMap<>();
        //testJSON.put("availableSpace", 0);
        //JSONObject jsonObject = new JSONObject(testJSON);
        //when(bookingService.isBookingAvailable(booking)).thenReturn(false);
        given(bookingService.isBookingAvailable(booking)).willReturn(true);
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
