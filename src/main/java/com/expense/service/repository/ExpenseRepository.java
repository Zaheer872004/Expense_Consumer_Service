package com.expense.service.repository;

import com.expense.service.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserId(String userId);

    List<Expense> findByUserIdAndCreatedAtBetween(String userId, String startDate, String endDate);

    Optional<Expense> findByUserIdAndExternalId(String userId, String externalId);

    List<Expense> findByUserIdAndMerchant(String userId, String merchant);




}
