package ru.maryKr.bootCrud.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import ru.maryKr.bootCrud.model.User;
import ru.maryKr.bootCrud.service.AdminServiceImpl;
import ru.maryKr.bootCrud.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final SuccessUserHandler successUserHandler;
    @Autowired
    private final MyPasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl service;


    public WebSecurityConfig(SuccessUserHandler successUserHandler, MyPasswordEncoder passwordEncoder) {
        this.successUserHandler = successUserHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/index/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/welcome", "/add_user/**", "/").permitAll()
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin.successHandler(successUserHandler).permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll());
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() throws UsernameNotFoundException {
        return email -> {
            User user = service.findByEmail(email);
            return user;
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder.getPasswordEncoder());
        return provider;
    }
}