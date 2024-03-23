package com.example.blog.controllers;

import com.example.blog.config.AppConstatnts;
import com.example.blog.entities.Post;
import com.example.blog.payloads.ApiResponse;
import com.example.blog.payloads.PostDTO;
import com.example.blog.payloads.PostResponse;
import com.example.blog.services.FileService;
import com.example.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(value = "/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userID}/category/{categoryID}")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDto, @PathVariable Integer userID, @PathVariable Integer categoryID) {
        Post resp = this.postService.createPost(postDto, categoryID, userID);
        return new ResponseEntity<PostDTO>(this.modelMapper.map(resp, PostDTO.class), HttpStatus.CREATED);
    }

    @GetMapping(value = "/user/{userID}")
    public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable Integer userID) {
        List<PostDTO> resp = this.postService.getAllPostByUSer(userID);
        return new ResponseEntity<List<PostDTO>>(resp, HttpStatus.CREATED);
    }

    @GetMapping(value = "/category/{categoryID}")
    public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable Integer categoryID) {
        List<PostDTO> resp = this.postService.getAllPostByUSer(categoryID);
        return new ResponseEntity<List<PostDTO>>(resp, HttpStatus.CREATED);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstatnts.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstatnts.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstatnts.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstatnts.SORT_DIR, required = false) String sortDir) {

        PostResponse resp = this.postService.getAllPost(pageSize, pageNumber, sortBy, sortDir);

        return new ResponseEntity<PostResponse>(resp, HttpStatus.CREATED);
    }

    @GetMapping(value = "/getPost/{postID}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Integer postID) {
        PostDTO resp = this.postService.getPost(postID);
        return new ResponseEntity<PostDTO>(resp, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/{postID}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postID) {
        this.postService.deletePost(postID);
        return new ResponseEntity<ApiResponse>(new ApiResponse("post deleted successfully", "true"), HttpStatus.CREATED);
    }

    @PutMapping(value = "/updatePost/{postID}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Integer postID) {
        PostDTO resp = this.postService.updatePost(postDTO, postID);
        return new ResponseEntity<PostDTO>(resp, HttpStatus.CREATED);
    }

    @GetMapping(value = "/search/{keywords}")
    public ResponseEntity<List<PostDTO>> searchPost(@PathVariable("keywords") String keywords) {
        List<PostDTO> resp = this.postService.searchPost(keywords);
        return new ResponseEntity<List<PostDTO>>(resp, HttpStatus.OK);
    }

    @PostMapping(value = "/image/upload/{postID}")
    public ResponseEntity<PostDTO> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("postID") Integer postID) throws IOException {
        
        PostDTO post = this.postService.getPost(postID);
        String filename = this.fileService.uploadImage(path, image);
        post.setImageName(filename);
        PostDTO resp = this.postService.updatePost(post, postID);
        return new ResponseEntity<PostDTO>(resp, HttpStatus.OK);
    }
}
