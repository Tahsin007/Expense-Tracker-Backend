package com.project.Expense.Tracker.Controller;

import com.project.Expense.Tracker.Entity.Categories;
import com.project.Expense.Tracker.Service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        try{
            Optional<List<Categories>> allCategories = categoryService.getAllCategories();
            log.info(allCategories.toString());
            if(allCategories.isPresent()){
                return new ResponseEntity<>(allCategories, HttpStatus.OK);
            }
            return new ResponseEntity<>("Categories data not available", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Categories category) {
        try{
            Categories category1 = categoryService.createCategory(category);
            return new ResponseEntity<>(category1,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Categories categoryDetails) {
        try{
            Categories categories = categoryService.updateCategory(id, categoryDetails);
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try{
            categoryService.deleteCategory(id);
            return new ResponseEntity<>("Category Deleted Successfully",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }
}
