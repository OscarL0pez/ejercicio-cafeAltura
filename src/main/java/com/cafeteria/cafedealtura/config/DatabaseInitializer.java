package com.cafeteria.cafedealtura.config;

import com.cafeteria.cafedealtura.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {
    @Bean
    public CommandLineRunner initRoles(RoleService roleService) {
        return args -> {
            roleService.createRoleIfNotExists("USER");
            roleService.createRoleIfNotExists("ADMIN");
        };
    }
}