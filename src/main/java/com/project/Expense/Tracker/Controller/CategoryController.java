package com.project.Expense.Tracker.Controller;

import com.project.Expense.Tracker.Entity.Categories;
import com.project.Expense.Tracker.Service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@Slf4j
@Tag(name = "Category Controller", description = "APIs for managing expense/income categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        Optional<List<Categories>> allCategories = categoryService.getAllCategories();
        log.info(allCategories.toString());
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Categories category) {
        Categories category1 = categoryService.createCategory(category);
        return new ResponseEntity<>(category1,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Categories categoryDetails) {
        Categories categories = categoryService.updateCategory(id, categoryDetails);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Category Deleted Successfully",HttpStatus.OK);
    }
}
