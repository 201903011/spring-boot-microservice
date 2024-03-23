package com.example.blog.services;

import com.example.blog.entities.User;
import com.example.blog.payloads.UserDTO;
import org.hibernate.service.spi.InjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO, Integer id);

    UserDTO getUserbyID(Integer id);

    User getUSerByEmail(String email);

    List<UserDTO> getAllUsers();

    void deleteUser(Integer id);
}
