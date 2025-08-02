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

    @Operation(
            description = "Get all available categories.",
            summary = "Get All Categories",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Categories not found", responseCode = "404")
            }
    )
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        try{
            Optional<List<Categories>> allCategories = categoryService.getAllCategories();
            log.info(allCategories.toString());
            if(allCategories.isPresent()){
                return new ResponseEntity<>(allCategories, HttpStatus.OK);
            }
            return new ResponseEntity<>("Categories data not available", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            description = "Create a new category.",
            summary = "Create Category",
            responses = {
                    @ApiResponse(description = "Category created successfully", responseCode = "201"),
                    @ApiResponse(description = "Bad request", responseCode = "400")
            }
    )
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Categories category) {
        try{
            Categories category1 = categoryService.createCategory(category);
            return new ResponseEntity<>(category1,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            description = "Update an existing category.",
            summary = "Update Category",
            responses = {
                    @ApiResponse(description = "Category updated successfully", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Categories categoryDetails) {
        try{
            Categories categories = categoryService.updateCategory(id, categoryDetails);
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            description = "Delete a category by its ID.",
            summary = "Delete Category",
            responses = {
                    @ApiResponse(description = "Category deleted successfully", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400")
            }
    )
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
