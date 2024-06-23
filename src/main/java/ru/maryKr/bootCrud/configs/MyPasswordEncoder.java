package ru.maryKr.bootCrud.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class MyPasswordEncoder {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public MyPasswordEncoder() {}

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
