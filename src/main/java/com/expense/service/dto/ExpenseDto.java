package com.expense.service.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseDto {

    private String externalId;

    @JsonProperty(value="user_id")
    private String userId;


    @JsonProperty(value="amount")
    private String amount;

    @JsonProperty(value="merchant")
    private String merchant;

    @JsonProperty(value="currency")
    private String currency;

    @JsonProperty(value="created_at")
    private Timestamp createdAt;

    @JsonProperty(value="updated_at")
    private Timestamp updatedAt;

    public ExpenseDto(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            ExpenseDto expense = mapper.readValue(json, ExpenseDto.class);
            this.externalId = expense.externalId;
            this.amount = expense.amount;
            this.userId = expense.userId;
            this.merchant = expense.merchant;
            this.currency = expense.currency;
            this.createdAt = expense.createdAt;
            this.updatedAt = expense.updatedAt;
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize ExpenseDto from JSON", e);
        }
    }

}
