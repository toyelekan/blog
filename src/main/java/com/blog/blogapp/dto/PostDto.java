package com.blog.blogapp.dto;

import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private Long id;
    @NotEmpty
    @Size(min = 2,message = "Post title should have atleast two characters")
    private String title;
    @NotEmpty
    @Size(min = 10,message = "Post description should have atleast ten characters")
    private String description;
    @NotEmpty
    private String content;
}
