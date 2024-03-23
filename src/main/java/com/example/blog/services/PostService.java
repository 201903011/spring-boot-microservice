package com.example.blog.services;

import com.example.blog.entities.Category;
import com.example.blog.entities.Post;
import com.example.blog.entities.User;
import com.example.blog.payloads.PostDTO;
import com.example.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {
    // create
    Post createPost(PostDTO postDTO, Integer userID, Integer categoryID);

    // update
    PostDTO updatePost(PostDTO postDTO, Integer postID);

    // delete
    void deletePost(Integer postID);

    // getpost
    PostDTO getPost(Integer postID);

    // get Paginated post
    PostResponse getAllPost(Integer pageSize, Integer pageNumber, String sortBy, String sortDir);

    // get post by category
    List<PostDTO> getAllPostByCategory(Integer categoryID);

    // get Paginated post
    List<PostDTO> getAllPostByUSer(Integer userID);

    //
    List<PostDTO> searchPost(String keyword);

}
