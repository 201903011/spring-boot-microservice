package com.example.blog.services.impl;

import com.example.blog.entities.Category;
import com.example.blog.entities.Post;
import com.example.blog.entities.User;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.PostDTO;
import com.example.blog.payloads.PostResponse;
import com.example.blog.repositories.CategoryRepo;
import com.example.blog.repositories.PostRepo;
import com.example.blog.repositories.UserRepo;
import com.example.blog.services.FileService;
import com.example.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public Post createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userID", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "userID", categoryId));

        Post post = this.modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post resp = this.postRepo.save(post);
        return resp;
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postID) {
        Post resp = this.postRepo.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post", "post", postID));
        resp.setTitle(postDTO.getTitle());
        resp.setContent(postDTO.getContent());
        resp.setImageName(postDTO.getImageName());

        Post updatedPost = this.postRepo.save(resp);
        return this.modelMapper.map(updatedPost, PostDTO.class);
    }

    @Override
    public void deletePost(Integer postID) {
        Post resp = this.postRepo.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post", "post", postID));
        this.postRepo.delete(resp);
    }

    @Override
    public PostDTO getPost(Integer postID) {
        Post resp = this.postRepo.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post", "post", postID));
        return this.modelMapper.map(resp, PostDTO.class);
    }

    @Override
    public PostResponse getAllPost(Integer pageSize, Integer pageNumber, String sortBy, String sortDir) {
//        int pageSize = 2;
//        int pageNumber = 1;
        Sort sort = null;
        if (sortDir.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePosts = this.postRepo.findAll(p);
        List<Post> posts = pagePosts.getContent();
        List<PostDTO> postDTOList = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        PostResponse resp = new PostResponse();
        resp.setContent(postDTOList);
        resp.setPageNumber(pagePosts.getNumber());
        resp.setPageSize(pagePosts.getSize());
        resp.setTotalPages(pagePosts.getTotalPages());
        resp.setTotalElements(pagePosts.getNumberOfElements());
        resp.setLastpage(pagePosts.isLast());

        return resp;
    }

    @Override
    public List<PostDTO> getAllPostByCategory(Integer categoryID) {
        Category cat = this.categoryRepo.findById(categoryID).orElseThrow(() -> new ResourceNotFoundException("Category", "category", categoryID));
        List<Post> posts = this.postRepo.findByCategory(cat);
        List<PostDTO> postDTOList = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        return postDTOList;
    }

    @Override
    public List<PostDTO> getAllPostByUSer(Integer userID) {
        User user = this.userRepo.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User", "user", userID));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDTO> postDTOList = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        return postDTOList;
    }

    @Override
    public List<PostDTO> searchPost(String keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        List<PostDTO> postDTOList = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        return postDTOList;
    }
}
