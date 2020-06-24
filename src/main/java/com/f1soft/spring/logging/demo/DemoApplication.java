package com.f1soft.spring.logging.demo;

import com.f1soft.spring.logging.demo.dto.CustomerDetail;
import com.f1soft.spring.logging.demo.marker.LoggingMarkers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        beginLogging();
    }

    public static void beginLogging() {
        try {
            CustomerDetail customerDetail = new CustomerDetail();
            customerDetail.setUsername("9808639594");
            customerDetail.setPassword("1234");
            customerDetail.setCardNo("1234567890123234");

            log.info("customer detail : " + customerDetail);

            String data = new ObjectMapper().writeValueAsString(customerDetail);

            log.info("data : " + data);

            // log.info("Passed to server::0084USER:17603,IP:0:0:0:0:0:0:0:1,3425,Credit Card 1:1000002367844224,3425,Credit Card2:1000002367844224 , CVV:234,SSN:123456789");
            //log.info(LoggingMarkers.JSON, "{\"ssn\": \"1234567890\"}");
        } catch (Exception e) {
            log.error("Exception ", e);
        }
    }
}
