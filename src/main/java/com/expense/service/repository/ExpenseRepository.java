package com.expense.service.repository;

import com.expense.service.entities.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    List<Expense> findByUserId(String userId);

    List<Expense> findByUserIdAndTransactionTypeAndCreatedAtBetween(String userId, String transactionType, LocalDateTime startTime, LocalDateTime endTime);

    List<Expense> findByUserIdAndMerchantAndCreatedAtBetween(String userId, String merchant, LocalDateTime startTime, LocalDateTime endTime);

    Optional<Expense> findByUserIdAndExternalId(String userId, String externalId);

//    Optional<Expense> findByUserIdAndTransactionType(String userId, String transactionType);

    List<Expense> findByUserIdAndMerchant(String userId, String merchant);
    
}