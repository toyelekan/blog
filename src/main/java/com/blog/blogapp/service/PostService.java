package com.blog.blogapp.service;

import com.blog.blogapp.dto.PostDto;
import com.blog.blogapp.dto.PostResponse;
import com.blog.blogapp.entity.Post;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(long id);
    PostDto updatePost(PostDto postDto, long id);
    void deletePostById(long id);
}
