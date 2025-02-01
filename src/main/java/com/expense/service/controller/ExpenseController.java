package com.expense.service.controller;


import com.expense.service.dto.ExpenseDto;
import com.expense.service.entity.Expense;
import com.expense.service.service.ExpenseService;
import jakarta.websocket.server.PathParam;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;






@RestController
@RequestMapping("/expense/v1")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


    @GetMapping("/getExpense")
    public ResponseEntity<List<ExpenseDto>> getExpenses(@PathParam("user_id") @NonNull String userId) {

        try {
            List<ExpenseDto> expenseList = expenseService.getExpenses(userId);
            return new ResponseEntity<>(expenseList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createExpense")
    public ResponseEntity<Boolean> createExpense(@RequestHeader(value = "X-User-Id") @NonNull String userId, ExpenseDto expenseDto) {

        try {
            expenseDto.setUserId(userId);
            boolean isCreated = expenseService.createExpense(expenseDto);
            return new ResponseEntity<>(isCreated, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateExpense")
    public ResponseEntity<Boolean> updateExpense(@RequestHeader(value = "X-User-Id") @NonNull String userId, @PathParam("external_id") String externalId, ExpenseDto expenseDto) {

        try {
            expenseDto.setUserId(userId);
            expenseDto.setExternalId(externalId);
            boolean isUpdated = expenseService.updateExpense(expenseDto);
            return new ResponseEntity<>(isUpdated, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
