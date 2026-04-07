package com.e_com.AuthService.Seed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.e_com.AuthService.Entity.Role;
import com.e_com.AuthService.Repository.IRoleRepository;

@Configuration
public class DatabaseSeeder {

    @Autowired
    public IRoleRepository roleRepository;

    @Bean
    CommandLineRunner seed() {
        return args -> {
            if (this.roleRepository.count() == 0) {
                this.roleRepository.save(new Role("System Administrator", "SYS_ADMIN"));
                this.roleRepository.save(new Role("System Staff", "SYS_STAFF"));
                this.roleRepository.save(new Role("Vendor Administrator", "VDR_ADMIN"));
                this.roleRepository.save(new Role("Vendor Staff", "VDR_STAFF"));
                this.roleRepository.save(new Role("Customer", "CUSTOMER"));
            }
        };
    }
}
