package com.packt.cantata;

import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class CantataApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CantataApplication.class);

    SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    public static void main(String[] args) {
        SpringApplication.run(CantataApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {


    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }

}
