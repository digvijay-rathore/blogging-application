package com.selflearning.blogging.bloggingapplicationapi.services.impl;

import com.selflearning.blogging.bloggingapplicationapi.entities.Category;
import com.selflearning.blogging.bloggingapplicationapi.entities.Post;
import com.selflearning.blogging.bloggingapplicationapi.entities.User;
import com.selflearning.blogging.bloggingapplicationapi.exceptions.ResourceNotFoundException;
import com.selflearning.blogging.bloggingapplicationapi.payloads.MyModelMapper;
import com.selflearning.blogging.bloggingapplicationapi.payloads.PostDto;
import com.selflearning.blogging.bloggingapplicationapi.payloads.PostResponse;
import com.selflearning.blogging.bloggingapplicationapi.repositories.CategoryRepository;
import com.selflearning.blogging.bloggingapplicationapi.repositories.PostRepository;
import com.selflearning.blogging.bloggingapplicationapi.repositories.UserRepository;
import com.selflearning.blogging.bloggingapplicationapi.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostServiceImpl implements PostService
{
    PostRepository postRepository;
    MyModelMapper modelMapper;
    UserRepository userRepository;
    CategoryRepository categoryRepository;

    public PostServiceImpl(MyModelMapper modelMapper, UserRepository userRepository,
                           CategoryRepository categoryRepository, PostRepository postRepository)
    {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId)
    {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);

        post.setCategory(category);
        post.setUser(user);
        post.setDate(LocalDate.now());
        post.setImageName("default.png");

        Post savedPost = this.postRepository.save(post);

        return this.modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId)
    {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setImageName(postDto.getImageName());
        post.setDate(postDto.getDate());

        Post updatedPost = postRepository.save(post);

        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId)
    {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        postRepository.delete(post);
    }

    @Override
    public PostDto getPostById(Integer postId)
    {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = postRepository.findAll(pageable);

        List<Post> posts = pagePost.getContent();
        List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).toList();

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId)
    {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

        List<Post> postList = postRepository.findByCategory(category);

        return postList.stream()
                .map((post) -> this.modelMapper.map(post, PostDto.class)).toList();
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        List<Post> posts = postRepository.findByUser(user);

        return posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).toList();
    }

    @Override
    public List<PostDto> searchPosts(String keywords)
    {
//        List<Post> posts = postRepository.findByTitleContaining(keywords);
        List<Post> posts = postRepository.mySearchByTitle("%" + keywords + "%");

        return posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).toList();
    }
}
