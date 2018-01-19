package com.springrest.configuration;

import com.springrest.repository.UserzRepository;
import com.springrest.services.UserzService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * Class that holds bean factory for the password encryption.
 * Ultimate goal is to apply JWT
 */

@Configuration
public class SecurityEncoderConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
