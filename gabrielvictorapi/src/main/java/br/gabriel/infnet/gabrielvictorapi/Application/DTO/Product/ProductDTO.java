package br.gabriel.infnet.gabrielvictorapi.Application.DTO.Product;

import java.time.LocalDateTime;

public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer amount;
    private LocalDateTime insertAt;
    private LocalDateTime updatedAt;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public LocalDateTime getInsertAt() {
        return insertAt;
    }
    public void setInsertAt(LocalDateTime insertAt) {
        this.insertAt = insertAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
