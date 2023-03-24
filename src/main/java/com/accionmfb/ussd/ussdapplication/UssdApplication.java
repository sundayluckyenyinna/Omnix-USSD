package com.accionmfb.ussd.ussdapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(AppConfig.class)
@SpringBootApplication
public class UssdApplication {

    public static void main(String[] args) {
        SpringApplication.run(UssdApplication.class, args);
    }

}
