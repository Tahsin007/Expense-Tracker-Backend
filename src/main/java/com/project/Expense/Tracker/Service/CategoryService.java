package com.project.Expense.Tracker.Service;

import com.project.Expense.Tracker.Entity.Categories;
import com.project.Expense.Tracker.Entity.User;
import com.project.Expense.Tracker.Exception.ResourceNotFoundException;
import com.project.Expense.Tracker.Exception.UnauthorizedAccessEcxception;
import com.project.Expense.Tracker.Repository.AuthRepository;
import com.project.Expense.Tracker.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthRepository authRepository;

    public Optional<List<Categories>> getAllCategories() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = authRepository.findByUserName(authentication.getName());
        return categoryRepository.findByUser_Id(user.getId());
    }

    public Categories createCategory(Categories category) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = authRepository.findByUserName(authentication.getName());
        category.setUser(user);
        return categoryRepository.save(category);
    }

    public Categories updateCategory(Long id, Categories categoryDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = authRepository.findByUserName(authentication.getName());
        Categories category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (!category.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessEcxception("Unauthorized to update this category");
        }
        category.setName(categoryDetails.getName());
        category.setIcon(categoryDetails.getIcon());
        category.setType(categoryDetails.getType());
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = authRepository.findByUserName(authentication.getName());

        Categories category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (!category.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessEcxception("Unauthorized to update this category");
        }

        categoryRepository.delete(category);
//        categoryRepository.deleteById(id);
    }
}
