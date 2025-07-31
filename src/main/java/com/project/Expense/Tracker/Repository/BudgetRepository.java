package com.project.Expense.Tracker.Repository;

import com.project.Expense.Tracker.Entity.Budgets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budgets,Long> {

    List<Budgets> findByUser_UserName(String userName);

    Optional<List<Budgets>> findByUser_UserNameAndYear(String userName,int year);

    Optional<Budgets> findByUser_UserNameAndMonth(String userName, int month);

}
