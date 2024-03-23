package com.example.blog.services.impl;

import com.example.blog.entities.Comment;
import com.example.blog.entities.Post;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.CommentDTO;
import com.example.blog.payloads.PostDTO;
import com.example.blog.repositories.CommentRepo;
import com.example.blog.repositories.PostRepo;
import com.example.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postID) {

        Post post = this.postRepo.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post", "Post", postID));
        Comment comment = this.modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        Comment savedCommenet = this.commentRepo.save(comment);
        CommentDTO resp = this.modelMapper.map(savedCommenet, CommentDTO.class);
        return resp;
    }

    @Override
    public void deleteComment(Integer commentID) {
        Comment comment = this.commentRepo.findById(commentID).orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment", commentID));
//        this.commentRepo.delete(comment);
    }
}
