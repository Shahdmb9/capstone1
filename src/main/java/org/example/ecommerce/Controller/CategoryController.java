package org.example.ecommerce.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.ApiResponse.ApiResponse;
import org.example.ecommerce.Model.Category;
import org.example.ecommerce.Services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        return categoryService.addCategory(category)?ResponseEntity.status(200).body(new ApiResponse("category added")):
                ResponseEntity.status(400).body(new ApiResponse("category already exists"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id,@RequestBody @Valid Category category,Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        return categoryService.updateCategory(id,category)?ResponseEntity.status(200).body(new ApiResponse("category updated")):
                ResponseEntity.status(200).body(new ApiResponse("category not exists"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        if(categoryService.removeCategory(id)){
            return ResponseEntity.status(200).body(new ApiResponse("category deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("category not found"));
    }
}
