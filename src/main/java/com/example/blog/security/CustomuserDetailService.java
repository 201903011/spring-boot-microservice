package com.example.blog.security;


import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.repositories.UserRepo;
import com.example.blog.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
//@Component("userDetailsService1")
public class CustomuserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
//    private final UserRepo userRepo;

    public CustomuserDetailService(UserRepo userRepository) {
        this.userRepo = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loading user by username
//        User user = this.userRepo.findByEmail(username).orElseThrow(
//                () -> new ResourceNotFoundException("User", "id", 0)
//        );
        return this.userRepo.findByEmail(username).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", 0)
        );
//        return null;

    }
}
