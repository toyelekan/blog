package com.blog.blogapp.service.impl;

import com.blog.blogapp.dto.PostDto;
import com.blog.blogapp.dto.PostResponse;
import com.blog.blogapp.entity.Post;
import com.blog.blogapp.exception.ResourceNotFoundException;
import com.blog.blogapp.repository.PostRepository;
import com.blog.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

//    public PostServiceImpl(PostRepository postRepository) {
//        this.postRepository = postRepository;
//    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToPost(postDto);
        Post newPost = postRepository.save(post);
        PostDto postResponse = mapToPostDto(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = 
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy));
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content = listOfPosts.stream().map(post -> mapToPostDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setLast(posts.isLast());
        postResponse.setPageNo(posts.getNumber());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","ID",id));
        return mapToPostDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToPostDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    private PostDto mapToPostDto(Post post){
        PostDto postResponse = new PostDto();
        postResponse.setId(post.getId());
        postResponse.setTitle(post.getTitle());
        postResponse.setContent(post.getContent());
        postResponse.setDescription(post.getDescription());
        return postResponse;
    }

    private Post mapToPost(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        return post;
    }


}
