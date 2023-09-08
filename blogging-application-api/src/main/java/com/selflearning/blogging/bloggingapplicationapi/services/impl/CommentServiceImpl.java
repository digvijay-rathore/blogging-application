package com.selflearning.blogging.bloggingapplicationapi.services.impl;

import com.selflearning.blogging.bloggingapplicationapi.entities.Comment;
import com.selflearning.blogging.bloggingapplicationapi.entities.Post;
import com.selflearning.blogging.bloggingapplicationapi.exceptions.ResourceNotFoundException;
import com.selflearning.blogging.bloggingapplicationapi.payloads.CommentDto;
import com.selflearning.blogging.bloggingapplicationapi.repositories.CommentRepository;
import com.selflearning.blogging.bloggingapplicationapi.repositories.PostRepository;
import com.selflearning.blogging.bloggingapplicationapi.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService
{
    CommentRepository commentRepository;
    PostRepository postRepository;
    ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper)
    {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId)
    {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId)
    {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));

        commentRepository.delete(comment);
    }
}
