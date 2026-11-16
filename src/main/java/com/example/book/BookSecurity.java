package com.example.book;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BookSecurity {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                        .requestMatchers("/books/**")
                        .hasRole("BOOK-OWNER"))
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }


    @Bean
    UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder){
        User.UserBuilder users = User.builder();
        UserDetails qodiriy = users
                .username("Qodiriy")
                .password(passwordEncoder.encode("q1234"))
                .roles("BOOK-OWNER")
                .build();

        UserDetails oybek = users
                .username("Oybek")
                .password(passwordEncoder.encode("o1234"))
                .roles("NON-OWNER")
                .build();

        UserDetails hank = users
                .username("hank-owns-no-book")
                .password(passwordEncoder.encode("h1234"))
                .roles("NON-OWNER")
                .build();

        return new InMemoryUserDetailsManager(qodiriy, oybek, hank);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
