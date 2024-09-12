package com.zkrallah.sanad;

import com.zkrallah.sanad.entity.Role;
import com.zkrallah.sanad.service.role.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SanadLawApplication {

    public static void main(String[] args) {
        SpringApplication.run(SanadLawApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleService roleService) {
        return args -> {
            Role role1 = roleService.saveRole(new Role(null, "ADMIN"));
            Role role2 = roleService.saveRole(new Role(null, "LAWYER"));
            Role role3 = roleService.saveRole(new Role(null, "CLIENT"));
        };
    }

}
