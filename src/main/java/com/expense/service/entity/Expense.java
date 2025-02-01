package com.expense.service.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Expense {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long  Id;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "amount")
    private String amount;

    @Column(name = "merchant")
    private String merchant;

    @Column(name = "currency")
    private String currency;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;


    // Define a formatter for your timestamps
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // This method is called once before the entity is persisted.
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now.format(FORMATTER);
        this.updatedAt = now.format(FORMATTER);

        // Generate the externalId if it's not already set
        if (this.externalId == null) {
            this.externalId = UUID.randomUUID().toString();
        }
    }

    // This method is called each time the entity is updated.
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now().format(FORMATTER);
    }


//    @PrePersist
//    @PreUpdate
//    private void generateExternalId() {
//        if(this.externalId == null) {
//            this.externalId = UUID.randomUUID().toString();
//        }
//    }



}
