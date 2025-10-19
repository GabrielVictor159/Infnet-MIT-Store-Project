package br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.Product;

import java.util.Optional;

public class AlterProductRequest {
    private Integer productId;
    private Optional<String> name;
    private Optional<String> description;
    private Optional<Double> price;
    private Optional<Integer> amount;
    private Optional<Integer> ownerId;
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public Optional<String> getName() {
        return name;
    }
    public void setName(Optional<String> name) {
        this.name = name;
    }
    public Optional<String> getDescription() {
        return description;
    }
    public void setDescription(Optional<String> description) {
        this.description = description;
    }
    public Optional<Double> getPrice() {
        return price;
    }
    public void setPrice(Optional<Double> price) {
        this.price = price;
    }
    public Optional<Integer> getAmount() {
        return amount;
    }
    public void setAmount(Optional<Integer> amount) {
        this.amount = amount;
    }
    public Optional<Integer> getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(Optional<Integer> ownerId) {
        this.ownerId = ownerId;
    }
}
