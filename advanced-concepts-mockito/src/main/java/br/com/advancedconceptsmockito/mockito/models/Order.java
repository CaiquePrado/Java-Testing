package br.com.advancedconceptsmockito.mockito.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order {
    
    private UUID id;
    private String productName;
    private Long amount;
    private LocalDateTime creationDate;
    
    public Order() {}

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}