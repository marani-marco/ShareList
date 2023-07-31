package com.marco.sharelist;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
@Configuration
public class ShareListApplication {

    private static final Logger logger = LoggerFactory.getLogger(ShareListApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ShareListApplication.class, args);
    }

}
