package com.example.blog.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

    @NotEmpty
    private Integer categoryId ;

    @NotEmpty
    private String categoryTitle ;

    @NotEmpty
    private String categoryDesc ;
}
