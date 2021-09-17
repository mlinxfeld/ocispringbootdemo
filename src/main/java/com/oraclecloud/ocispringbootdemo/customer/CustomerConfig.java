package com.oraclecloud.ocispringbootdemo.customer;

import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            CustomerRepository repository) {
        return args -> {
            Customer luke = new Customer(
                    "Luke",
                    "Feldman",
                    "lukasz.feldman@oracle.com"
            );
            Customer martin = new Customer(
                    "Martin",
                    "Feldman",
                    "martin.feldman@oracle.com"
            );

            repository.saveAll(
                    Arrays.asList(luke, martin)
            );
        };
    }
}
