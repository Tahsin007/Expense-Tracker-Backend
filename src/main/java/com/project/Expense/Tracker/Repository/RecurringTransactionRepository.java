package com.project.Expense.Tracker.Repository;

import com.project.Expense.Tracker.Entity.RecurringTransactions;
import com.project.Expense.Tracker.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransactions, Long> {
    List<RecurringTransactions> findByUserId(Long userId);

    List<RecurringTransactions> findByUserAndNextOccurrenceAfter(User user, LocalDate date);

    List<RecurringTransactions> findByUserAndNextOccurrenceBeforeAndEndDateAfter(User user, LocalDate date, LocalDate endDate);
}
