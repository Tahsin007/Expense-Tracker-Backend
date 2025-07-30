package com.project.Expense.Tracker.Repository;

import com.project.Expense.Tracker.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Transaction,Long> {
//    @Query("SELECT e FROM Expense e WHERE MONTH(e.createdAt) = MONTH(CURRENT_DATE) AND YEAR(e.createdAt) = YEAR(CURRENT_DATE)")
    @Query("SELECT MONTH(e.date), SUM(e.amount) FROM Transaction e WHERE e.user.id = :userId GROUP BY MONTH(e.date)")
    List<Object[]> findMonthlyTotalsByUserId(Long userId);

    List<Transaction> findByCategory_Name(String category);

    List<Transaction> findByUser_UserName(String userName);


}
