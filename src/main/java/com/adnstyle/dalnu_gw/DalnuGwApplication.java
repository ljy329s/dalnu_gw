package com.adnstyle.dalnu_gw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DalnuGwApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(DalnuGwApplication.class, args);
    }
    
}
