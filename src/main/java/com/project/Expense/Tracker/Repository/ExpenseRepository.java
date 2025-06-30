package com.project.Expense.Tracker.Repository;

import com.project.Expense.Tracker.Entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {
//    @Query("SELECT e FROM Expense e WHERE MONTH(e.createdAt) = MONTH(CURRENT_DATE) AND YEAR(e.createdAt) = YEAR(CURRENT_DATE)")
    @Query("SELECT MONTH(e.createdAt), SUM(e.amount) FROM Expense e WHERE e.user.id = :userId GROUP BY MONTH(e.createdAt)")
    List<Object[]> findMonthlyTotalsByUserId(Long userId);

    List<Expense> findByCategory(String category);

    List<Expense> findByUser_UserName(String userName);


}
