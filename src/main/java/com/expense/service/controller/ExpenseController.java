package com.expense.service.controller;

import com.expense.service.dto.ExpenseDto;
import com.expense.service.service.ExpenseService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/expense/v1")
public class ExpenseController
{

    private final ExpenseService expenseService;

    @Autowired
    ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    @GetMapping(path = "/getExpense")
    public ResponseEntity<List<ExpenseDto>> getExpense(@RequestHeader(value = "X-User-Id") @NonNull String userId){
        try{
            List<ExpenseDto> expenseDtoList = expenseService.getExpenses(userId);
            return new ResponseEntity<>(expenseDtoList, HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ New: Get expenses by transaction type and date range
    @GetMapping(path = "/getExpense/by-type")
    public ResponseEntity<List<ExpenseDto>> getExpenseByType(
            @RequestHeader(value = "X-User-Id") @NonNull String userId,
            @RequestParam String transactionType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ) {
        try {
            List<ExpenseDto> expenseDtoList = expenseService.getExpensesByUserAndTypeAndDateRange(userId, transactionType.toLowerCase(), startTime, endTime);
            return new ResponseEntity<>(expenseDtoList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ New: Get expenses by merchant and date range
    @GetMapping(path = "/getExpense/by-merchant-date")
    public ResponseEntity<List<ExpenseDto>> getExpenseByMerchantWithDate(
            @RequestHeader(value = "X-User-Id") @NonNull String userId,
            @RequestParam  String merchant,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ) {
        try {
            List<ExpenseDto> expenseDtoList = expenseService.getExpensesByUserAndMerchantAndDateRange(userId, merchant.toLowerCase(), startTime, endTime);
            return new ResponseEntity<>(expenseDtoList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/getExpense/by-merchant")
    public ResponseEntity<List<ExpenseDto>> getExpenseByMerchant(
            @RequestHeader(value = "X-User-Id") @NonNull String userId,
            @RequestParam String merchant
    ) {
        try {
            List<ExpenseDto> expenseDtoList = expenseService.getExpenseByUserAndMerchant(userId,merchant.toLowerCase());
            return new ResponseEntity<>(expenseDtoList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(path="/updateExpense")
    public ResponseEntity<Boolean> updateExpense(@RequestHeader(value = "X-User-Id") @NonNull String userId, @RequestBody ExpenseDto expenseDto){
        try{
            expenseDto.setUserId(userId);
            Boolean isUpdated = expenseService.updateExpense(expenseDto);
            return new ResponseEntity<>(isUpdated, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="/addExpense")
    public ResponseEntity<Boolean> addExpenses(@RequestHeader(value = "X-User-Id") @NonNull String userId, @RequestBody ExpenseDto expenseDto){
        try{
            expenseDto.setUserId(userId);
            return new ResponseEntity<>(expenseService.createExpense(expenseDto), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Boolean> checkHealth(){
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}