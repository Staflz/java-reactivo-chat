package com.example.chat.component;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Component to verify that Spring Boot context starts successfully.
 * If this runs, it confirms the application context initialized properly.
 */
@Component
@Slf4j
public class StartupCheck implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.info("✅ Spring Boot context started successfully!");
        log.info("🎉 Chat application is ready to accept connections");
    }
}