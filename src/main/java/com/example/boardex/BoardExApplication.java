package com.example.boardex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BoardExApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardExApplication.class, args);
    }

}
