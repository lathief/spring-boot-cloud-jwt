package com.project.demo.config;

import com.project.demo.model.Role;
import com.project.demo.model.enumerations.ERoles;
import com.project.demo.repository.RoleRepository;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    Config(RoleRepository roleRepository) {
        for (ERoles c : ERoles.values()) {
            try {
                Role roles = roleRepository.findByName(c)
                        .orElseThrow(() -> new RuntimeException("Roles not found"));
            } catch (RuntimeException rte) {
                Role roles = new Role();
                roles.setName(c);
                roleRepository.save(roles);
            }
        }
    }
}
