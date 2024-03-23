package com.example.blog.controllers;

import com.example.blog.entities.User;
import com.example.blog.payloads.ApiResponse;
import com.example.blog.payloads.UserDTO;
import com.example.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired(required = true)
    private UserService userService;

    //Post Create user
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createuser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createUserDTO = this.userService.createUser(userDTO);
        return new ResponseEntity<UserDTO>(createUserDTO, HttpStatus.CREATED);
    }

    //Put Update user
    @PutMapping("/update/{userID}")
    public ResponseEntity<UserDTO> updateuser(@Valid @RequestBody UserDTO userDTO, @PathVariable("userID") Integer userID) {
        UserDTO updateuser = this.userService.updateUser(userDTO, userID);
        return new ResponseEntity<UserDTO>(updateuser, HttpStatus.OK);
    }

    //Delete Delete user
    @DeleteMapping("/delete/{userID}")
    public ResponseEntity<ApiResponse> deleteuser(@PathVariable("userID") Integer userID) {
        this.userService.deleteUser(userID);
        return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully", "true"), HttpStatus.OK);
    }

    //Get user
    @GetMapping("/get")
    public ResponseEntity<List<UserDTO>> getallusers() {
        List<UserDTO> usersList = this.userService.getAllUsers();
        return new ResponseEntity<List<UserDTO>>(usersList, HttpStatus.OK);
    }


    @GetMapping("get/{userID}")
    public ResponseEntity<UserDTO> getallusers(@PathVariable("userID") Integer userID) {
        UserDTO user = this.userService.getUserbyID(userID);

        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
    }


    @GetMapping("get/userbyemail/{userID}")
    public ResponseEntity<User> getalluserByEmail(@PathVariable("userID") String userID) {
        User user = this.userService.getUSerByEmail(userID);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
