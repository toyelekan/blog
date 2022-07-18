package com.blog.blogapp.service.impl;

import com.blog.blogapp.dto.CommentDto;
import com.blog.blogapp.entity.Comment;
import com.blog.blogapp.entity.Post;
import com.blog.blogapp.exception.BlogAPIException;
import com.blog.blogapp.exception.ResourceNotFoundException;
import com.blog.blogapp.repository.CommentRepository;
import com.blog.blogapp.repository.PostRepository;
import com.blog.blogapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;
    
    @Override
    public CommentDto craeteComment(long postID, CommentDto commentDto) {
        Comment comment = mapToComment(commentDto);
        Post post = postRepository.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post","ID",postID));
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return mapToCommentDto(savedComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postID) {
        List<Comment> comments = commentRepository.findCommentsByPostId(postID);
        return comments.stream().map(comment -> mapToCommentDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postID, long commentID) {
        Post post = postRepository.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post","ID",postID));
        Comment comment = commentRepository.findById(commentID).orElseThrow(() -> new ResourceNotFoundException("Comment","ID",commentID));
        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        return mapToCommentDto(comment);
    }

    @Override
    public CommentDto updateComment(long postID, long commentID, CommentDto commentDtoRequest) {
        Post post = postRepository.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post","ID",postID));
        Comment comment = commentRepository.findById(commentID).orElseThrow(() -> new ResourceNotFoundException("Comment","ID",commentID));
        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");

        comment.setName(commentDtoRequest.getName());
        comment.setEmail(commentDtoRequest.getEmail());
        comment.setBody(commentDtoRequest.getBody());
        Comment updatedComment = commentRepository.save(comment);

        return mapToCommentDto(updatedComment);
    }

    @Override
    public void deleteComment(long postID, long commentID) {
        Post post = postRepository.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post","ID",postID));
        Comment comment = commentRepository.findById(commentID).orElseThrow(() -> new ResourceNotFoundException("Comment","ID",commentID));
        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        commentRepository.delete(comment);
    }

    private CommentDto mapToCommentDto(Comment comment){
            CommentDto CommentResponse = new CommentDto();
            CommentResponse.setId(comment.getId());
            CommentResponse.setBody(comment.getBody());
            CommentResponse.setEmail(comment.getEmail());
            CommentResponse.setName(comment.getName());
            return CommentResponse;
        }

        private Comment mapToComment(CommentDto commentDto){
            Comment comment = new Comment();
            comment.setId(commentDto.getId());
            comment.setBody(commentDto.getBody());
            comment.setEmail(commentDto.getEmail());
            comment.setName(commentDto.getName());
            return comment;
        }
    }


