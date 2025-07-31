package com.project.Expense.Tracker.Repository;

import com.project.Expense.Tracker.Entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {
    Optional<List<Categories>> findByUser_Id(Long userId);
}
