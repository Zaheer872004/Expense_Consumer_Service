package com.expense.service.consumer;


import com.expense.service.dto.ExpenseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;



public class ExpenseDeserializer implements Deserializer<ExpenseDto> {


    @Override
    public ExpenseDto deserialize(String arg0, byte[] arg1) {

        ObjectMapper mapper = new ObjectMapper();
        ExpenseDto expenseDto = null;
        try {
            expenseDto = mapper.readValue(arg1, ExpenseDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return expenseDto;
    }


}
