package com.example.blog.controllers;

import com.example.blog.payloads.ApiResponse;
import com.example.blog.payloads.CommentDTO;
import com.example.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create/{postID}")
    public ResponseEntity<CommentDTO> createComment(
            @RequestBody CommentDTO comment,
            @PathVariable("postID") Integer postID
    ) {
        CommentDTO resp = this.commentService.createComment(comment, postID);
        return new ResponseEntity<CommentDTO>(resp, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{commentID}")
    public ResponseEntity<ApiResponse> deleteComment(
            @PathVariable("commentID") Integer commentID
    ) {
        this.commentService.deleteComment(commentID);
        return new ResponseEntity<ApiResponse>(
                new ApiResponse("comment deleted successfully", "true"),
                HttpStatus.OK
        );
    }
}
