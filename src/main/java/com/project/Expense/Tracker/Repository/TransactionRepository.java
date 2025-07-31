package com.project.Expense.Tracker.Repository;

import com.project.Expense.Tracker.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
//    @Query("SELECT e FROM Expense e WHERE MONTH(e.createdAt) = MONTH(CURRENT_DATE) AND YEAR(e.createdAt) = YEAR(CURRENT_DATE)")
    @Query("SELECT MONTH(e.date), SUM(e.amount) FROM Transaction e WHERE e.user.id = :userId GROUP BY MONTH(e.date)")
    List<Object[]> findMonthlyTotalsByUserId(Long userId);

    List<Transaction> findByCategory_Name(String category);

    List<Transaction> findByUser_Id(Long userId);

    @Query("SELECT t FROM Transaction t WHERE t.user.userName = :userName AND YEAR(t.date) = :year")
    List<Transaction> findYearlyTransactions(@Param("userName") String userName, @Param("year") int year);

    @Query("SELECT t FROM Transaction t WHERE t.user.userName = :userName AND YEAR(t.date) = :year AND MONTH(t.date) = :month")
    List<Transaction> findMonthlyTransactions(@Param("userName") String userName, @Param("month") int month, @Param("year") int year);




}
