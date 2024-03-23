package com.example.blog.services;

import com.example.blog.payloads.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService{
    // create
    public CategoryDTO createCategory(CategoryDTO categoryDTO);

    // update
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryID);

    // get
    public CategoryDTO getCategory(Integer categoryID);

    // getAll
    public List<CategoryDTO> getAllCategory();

    // delete
    public void deleteCategory(Integer categoryID);

}
