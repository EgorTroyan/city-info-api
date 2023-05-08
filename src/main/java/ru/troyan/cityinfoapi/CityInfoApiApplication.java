package ru.troyan.cityinfoapi;

import io.github.fastily.jwiki.core.Wiki;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CityInfoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityInfoApiApplication.class, args);
    }
    @Bean
    public Wiki wiki() {
        return new Wiki.Builder().build();
    }
}
