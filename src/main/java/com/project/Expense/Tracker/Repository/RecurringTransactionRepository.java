package com.project.Expense.Tracker.Repository;

import com.project.Expense.Tracker.Entity.RecurringTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransactions, Long> {
    List<RecurringTransactions> findByUserId(Long userId);
}
