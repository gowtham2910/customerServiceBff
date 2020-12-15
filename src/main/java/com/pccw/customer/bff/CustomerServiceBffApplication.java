package com.pccw.customer.bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * SprintBoot application class
 *
 * @author 40003450
 */

@SpringBootApplication
@ComponentScan(basePackages = "com.pccw.*")
public class CustomerServiceBffApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceBffApplication.class, args);
    }
}
