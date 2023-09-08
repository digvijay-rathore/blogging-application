package com.selflearning.blogging.bloggingapplicationapi.services;

import com.selflearning.blogging.bloggingapplicationapi.payloads.PostDto;
import com.selflearning.blogging.bloggingapplicationapi.payloads.PostResponse;

import java.util.List;

public interface PostService
{
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    PostDto getPostById(Integer postId);

    PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir);

    List<PostDto> getPostsByCategory(Integer categoryId);

    List<PostDto> getPostsByUser(Integer userId);

    List<PostDto> searchPosts(String keywords);
}
