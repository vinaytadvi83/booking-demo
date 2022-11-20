package com.mytest.maersk.controller;

import com.mytest.maersk.helper.ServiceHelper;
import com.mytest.maersk.repository.BookingRepository;
import com.mytest.maersk.service.BookingService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingControllerIntegrationTest {

    @TestConfiguration
    static class BookingServiceImplTestContextConfiguration {
        @Bean
        public BookingService employeeService() {
            return new BookingService() {
            };
        }
    }

    @Autowired
    private BookingService employeeService;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private ServiceHelper helper;

}
