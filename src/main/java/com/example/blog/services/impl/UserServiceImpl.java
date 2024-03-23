package com.example.blog.services.impl;

import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.UserDTO;
import com.example.blog.repositories.UserRepo;
import com.example.blog.services.UserService;
import com.example.blog.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = this.dtotoUser(userDTO);
        User savedUser = this.userRepo.save(user);
        return this.usertoDto(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer id) {
        User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("", "Id", id));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());

        User updatedUser = this.userRepo.save(user);
        UserDTO updatedUserDTO = this.usertoDto(updatedUser);
        return updatedUserDTO;
    }

    @Override
    public UserDTO getUserbyID(Integer id) {
        User user = this.userRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );
        UserDTO userDTO = this.usertoDto(user);
        return userDTO;
    }

    @Override
    public User getUSerByEmail(String email) {
        User user = this.userRepo.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", 0)
        );
        return user;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> userList = this.userRepo.findAll();

        List<UserDTO> userDTOList = userList.stream().map(user -> this.usertoDto(user)).collect(Collectors.toList());
        return userDTOList;
    }

    @Override
    public void deleteUser(Integer id) {
        User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "ID", id));
        this.userRepo.delete(user);
    }

    private User dtotoUser(UserDTO userDTO) {
//        User user = new User();
//        user.setId( userDTO.getId());
//        user.setName( userDTO.getName());
//        user.setEmail( userDTO.getEmail());
//        user.setPassword( userDTO.getPassword());
//        user.setAbout( userDTO.getAbout());

//        return user;
        return this.modelMapper.map(userDTO, User.class);
    }

    private UserDTO usertoDto(User user) {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(user.getId());
//        userDTO.setName(user.getName());
//        userDTO.setEmail(user.getEmail());
//        userDTO.setPassword(user.getPassword());
//        userDTO.setAbout(user.getAbout());
//        return userDTO;
        return this.modelMapper.map(user, UserDTO.class);
    }

}
