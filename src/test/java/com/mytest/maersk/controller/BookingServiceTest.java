package com.mytest.maersk.controller;

import com.mytest.maersk.gen.ServiceHelper;
import com.mytest.maersk.model.Booking;
import com.mytest.maersk.model.ContainerType;
import com.mytest.maersk.service.BookingService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    ServiceHelper serviceHelper; //= Mockito.mock(ServiceHelper.class);
    @InjectMocks
    BookingService bookingService;

    @Test
    void falseWhenAvailableSpaceZero() {
        Map<String, Integer> testJSON = new HashMap<>();
        testJSON.put("availableSpace", 0);
        JSONObject jsonObject = new JSONObject(testJSON);
        when(serviceHelper.callService("maersk-endpoints.endpoints.inquiry")).thenReturn(jsonObject);
        boolean result = bookingService.isBookingAvailable(new Booking(123456789L, 20, ContainerType.REEFER, "London", "Dovar", 6, "2020-10-12T13:53:09Z"));
        assertThat(result).isEqualTo(false);
    }

    @Test
    void trueWhenAvailableSpaceNotZeroAndEnoughSpaceForRequiredQuantity() {
        Map<String, Integer> testJSON = new HashMap<>();
        testJSON.put("availableSpace", 10);
        JSONObject jsonObject = new JSONObject(testJSON);
        when(serviceHelper.callService("maersk-endpoints.endpoints.inquiry")).thenReturn(jsonObject);
        boolean result = bookingService.isBookingAvailable(new Booking(123456789L, 20, ContainerType.REEFER, "London", "Dovar", 6, "2020-10-12T13:53:09Z"));
        assertThat(result).isEqualTo(true);
    }
}
