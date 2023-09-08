package com.selflearning.blogging.bloggingapplicationapi.controllers;

import com.selflearning.blogging.bloggingapplicationapi.payloads.ApiResponse;
import com.selflearning.blogging.bloggingapplicationapi.payloads.CommentDto;
import com.selflearning.blogging.bloggingapplicationapi.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController
{
    CommentService commentService;

    public CommentController(CommentService commentService)
    {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId)
    {
        CommentDto savedCommentDto = commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(savedCommentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId)
    {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment deleted successfully!", true),
                                    HttpStatus.OK);
    }
}
