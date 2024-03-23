package com.example.blog.controllers;

import com.example.blog.payloads.ApiResponse;
import com.example.blog.payloads.CategoryDTO;
import com.example.blog.services.CategoryService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // create
    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO){
        CategoryDTO resp = this.categoryService.createCategory(categoryDTO);
        return new ResponseEntity<CategoryDTO>(resp,HttpStatus.CREATED);
    }

    // update
    @PutMapping("/update/{categoryID}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO,@PathVariable("categoryID") Integer id ){
        CategoryDTO resp = this.categoryService.updateCategory(categoryDTO,id);
        return new ResponseEntity<CategoryDTO>(resp,HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/delete/{categoryID}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryID") Integer id ){
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted successfully","false"),HttpStatus.OK);
    }

    // get
    @GetMapping("/get/{categoryID}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("categoryID") Integer id){
        CategoryDTO resp =  this.categoryService.getCategory(id);
        return new ResponseEntity<CategoryDTO>(resp,HttpStatus.OK);
    }

    // get all
    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDTO>> getAllCategory(){
        List<CategoryDTO> resp =  this.categoryService.getAllCategory();
        return new ResponseEntity<List<CategoryDTO>>(resp,HttpStatus.OK);
    }

}
