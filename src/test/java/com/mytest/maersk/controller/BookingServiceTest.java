package com.mytest.maersk.controller;

import com.mytest.maersk.BookingApplication;
import com.mytest.maersk.helper.ServiceHelper;
import com.mytest.maersk.model.Booking;
import com.mytest.maersk.model.ContainerType;
import com.mytest.maersk.service.BookingService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingServiceTest {

    @MockBean
    ServiceHelper serviceHelper;
    @Autowired
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
