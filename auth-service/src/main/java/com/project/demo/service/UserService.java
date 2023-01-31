package com.project.demo.service;

import com.project.demo.model.Users;
import com.project.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UsersRepository usersRepository;
    @Override
    public Users loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username).orElseGet(null);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Username %s is not found", username));
        }
        return user;
    }
}
