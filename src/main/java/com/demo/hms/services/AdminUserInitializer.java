package com.demo.hms.services;

import com.demo.hms.entity.User;
import com.demo.hms.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer {

    @Bean
    public CommandLineRunner createAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
          if(userRepository.findByUsername("admin@gmail.com").isEmpty()) {
              User admin = new User();
              admin.setUsername("admin@gmail.com");
              admin.setPassword(passwordEncoder.encode("admin1234"));
              admin.setRole("ROLE_ADMIN");
              userRepository.save(admin);

              System.out.println("Default Admin User Created!");
          }

            if(userRepository.findByUsername("user1").isEmpty()) {
                User user1 = new User();
                user1.setUsername("user1");
                user1.setPassword(passwordEncoder.encode("user1234"));
                user1.setRole("ROLE_USER");
                userRepository.save(user1);

                System.out.println("Default User1 User Created!");
            }
        };
    }
}
