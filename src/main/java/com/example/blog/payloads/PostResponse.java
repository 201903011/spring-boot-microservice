package com.example.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {

    private Integer pageNumber;

    private Integer pageSize;

    private Integer totalElements;

    private Integer totalPages;

    private Boolean lastpage;

    private List<PostDTO> content;


}
