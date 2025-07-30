package com.project.Expense.Tracker.Repository;

import com.project.Expense.Tracker.Entity.Budgets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budgets,Long> {

}
