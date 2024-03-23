package com.example.blog.services;

import com.example.blog.entities.Comment;
import com.example.blog.payloads.CommentDTO;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO, Integer postID);

    void deleteComment(Integer commentID);
}
