package com.example.blog.services.impl;

import com.example.blog.entities.Category;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.CategoryDTO;
import com.example.blog.repositories.CategoryRepo;
import com.example.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper ;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = this.modelMapper.map(categoryDTO,Category.class);
        Category resp = this.categoryRepo.save(category);
        return this.modelMapper.map(resp,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryID) {
        Category oldCategory = this.categoryRepo.findById(categoryID).orElseThrow(()->new ResourceNotFoundException("Category","CategoryID",categoryID));
        Category category = this.modelMapper.map(categoryDTO,Category.class);
        oldCategory.setCategoryTitle(category.getCategoryTitle());
        oldCategory.setCategoryDesc(category.getCategoryDesc());

        Category resp = this.categoryRepo.save(oldCategory);
        return this.modelMapper.map(resp,CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategory(Integer categoryID) {
        Category resp = this.categoryRepo.findById(categoryID).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryID",categoryID));
        return this.modelMapper.map(resp,CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> categoryList = this.categoryRepo.findAll();
        List<CategoryDTO> respList = categoryList.stream().map((category -> this.modelMapper.map(category,CategoryDTO.class))).collect(Collectors.toList());
        return respList;
    }

    @Override
    public void deleteCategory(Integer categoryID) {
        this.categoryRepo.deleteById(categoryID);
    }
}
