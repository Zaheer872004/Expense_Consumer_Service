package com.expense.service.consumer;


import com.expense.service.dto.ExpenseDto;
import com.expense.service.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ExpenseConsumer {

    private ExpenseService expenseService;

    @Autowired
    public ExpenseConsumer(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ExpenseDto expenseDto) {

        try {
            System.out.println("Consumed JSON Message: " + expenseDto);
            expenseService.createExpense(expenseDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
