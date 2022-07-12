package com.blog.blogapp.controller;

import com.blog.blogapp.dto.PostDto;
import com.blog.blogapp.dto.PostResponse;
import com.blog.blogapp.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    //create blog post
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto),HttpStatus.CREATED);
    }

    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int  pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int  pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = "id", required = false) String  sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String  sortDir){
        return postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@RequestBody PostDto postDto, @PathVariable long id){
        PostDto postDtoResponse  = postService.updatePost(postDto,id);
        return new ResponseEntity<>(postDtoResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id){
        postService.deletePostById(id);
        return  new ResponseEntity<>("Post Entity Deleted Successfully",HttpStatus.OK);
    }
}

