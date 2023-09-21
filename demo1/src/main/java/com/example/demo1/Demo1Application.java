package com.example.demo1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo1Application {

    public static void main(String[] args) {
        final Logger logger = LogManager.getLogger("log4j2Logger");
        SpringApplication.run(Demo1Application.class, args);
    }
}
