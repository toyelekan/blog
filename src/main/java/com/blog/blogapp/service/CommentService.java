package com.blog.blogapp.service;

import com.blog.blogapp.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto craeteComment(long postID, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postID);
    CommentDto getCommentById(long postID, long commentID);
    CommentDto updateComment(long postID, long commentID, CommentDto commentDtoRequest);
    void deleteComment(long postID, long commentID);
}
