package com.teammanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SportsTeamManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SportsTeamManagerApplication.class, args);
    }
} 