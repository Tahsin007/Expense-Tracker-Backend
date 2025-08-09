package com.project.Expense.Tracker.Service;

import com.project.Expense.Tracker.Entity.CategoryType;
import com.project.Expense.Tracker.Entity.DTO.RecurringTransactionSummaryDTO;
import com.project.Expense.Tracker.Entity.RecurringTransactions;
import com.project.Expense.Tracker.Entity.User;
import com.project.Expense.Tracker.Exception.ApiException;
import com.project.Expense.Tracker.Repository.AuthRepository;
import com.project.Expense.Tracker.Repository.RecurringTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class RecurringTransactionService {

    @Autowired
    private RecurringTransactionRepository recurringTransactionRepository;

    @Autowired
    private AuthRepository authRepository;

    public List<RecurringTransactions> getAllRecurringTransactions() {
        User user = getCurrentUser();
        return recurringTransactionRepository.findByUserId(user.getId());
    }

    public RecurringTransactions getRecurringTransactionById(Long id) {
        User user = getCurrentUser();
        return recurringTransactionRepository.findById(id)
                .filter(transaction -> transaction.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ApiException("Recurring transaction not found or access denied", HttpStatus.NOT_FOUND));
    }

    public RecurringTransactions createRecurringTransaction(RecurringTransactions recurringTransaction) {
        User user = getCurrentUser();
        recurringTransaction.setUser(user);
        if (!recurringTransaction.isActive()) {
            recurringTransaction.setActive(true);
        }
        return recurringTransactionRepository.save(recurringTransaction);
    }

    public RecurringTransactions updateRecurringTransaction(Long id, RecurringTransactions updatedTransaction) {
        User user = getCurrentUser();
        RecurringTransactions existingTransaction = getRecurringTransactionById(id);

        if (!existingTransaction.getUser().getId().equals(user.getId())) {
            throw new ApiException("You are not authorized to update this transaction", HttpStatus.UNAUTHORIZED);
        }

        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setDescription(updatedTransaction.getDescription());
        existingTransaction.setStartDate(updatedTransaction.getStartDate());
        existingTransaction.setEndDate(updatedTransaction.getEndDate());
        existingTransaction.setNextOccurrence(updatedTransaction.getNextOccurrence());
        existingTransaction.setFrequency(updatedTransaction.getFrequency());
        existingTransaction.setCategory(updatedTransaction.getCategory());
        existingTransaction.setActive(updatedTransaction.isActive());
        existingTransaction.setMaxOccurrences(updatedTransaction.getMaxOccurrences());

        return recurringTransactionRepository.save(existingTransaction);
    }

    public void deleteRecurringTransaction(Long id) {
        User user = getCurrentUser();
        RecurringTransactions transaction = getRecurringTransactionById(id);

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new ApiException("You are not authorized to delete this transaction", HttpStatus.UNAUTHORIZED);
        }

        recurringTransactionRepository.deleteById(id);
    }

    public List<RecurringTransactions> getUpcomingTransactions() {
        User user = getCurrentUser();
        return recurringTransactionRepository.findByUserAndNextOccurrenceAfter(user, LocalDate.now());
    }

    public List<RecurringTransactions> getOverdueTransactions() {
        User user = getCurrentUser();
        return recurringTransactionRepository.findByUserAndNextOccurrenceBeforeAndEndDateAfter(user, LocalDate.now(), LocalDate.now());
    }

    public RecurringTransactionSummaryDTO getRecurringTransactionSummary(int year, int month) {
        User user = getCurrentUser();
        List<RecurringTransactions> recurringTransactions = recurringTransactionRepository.findAllByUser(user);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (RecurringTransactions transaction : recurringTransactions) {
            LocalDate nextOccurrence = transaction.getNextOccurrence();
            if (nextOccurrence.getYear() == year && nextOccurrence.getMonthValue() == month) {
                if (Objects.equals(transaction.getType(), "INCOME")) {
                    totalIncome = totalIncome.add(transaction.getAmount());
                } else if (Objects.equals(transaction.getType(), "EXPENSE")) {
                    totalExpense = totalExpense.add(transaction.getAmount());
                }
            }
        }

        return new RecurringTransactionSummaryDTO(totalIncome, totalExpense);
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = authRepository.findByUserName(username);
        if (user == null) {
            throw new ApiException("User not found", HttpStatus.NOT_FOUND);
        }
        return user;
    }
}
