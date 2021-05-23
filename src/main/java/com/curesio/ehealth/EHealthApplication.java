package com.curesio.ehealth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.io.File;

@SpringBootApplication
public class EHealthApplication {

    @Value("${documents.kyc.path}")
    private String userDocumentsPath;

    @PostConstruct
    private void makeDirsIfNotExist() {
        this.userDocumentsPath = System.getProperty("user.home") + this.userDocumentsPath;
        File file = new File(this.userDocumentsPath);

        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(EHealthApplication.class, args);
    }

    @Primary
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
