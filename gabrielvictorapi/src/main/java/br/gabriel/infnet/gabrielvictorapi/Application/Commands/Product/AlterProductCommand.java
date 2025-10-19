package br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product;

import java.util.Optional;

import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;
import jakarta.validation.constraints.Size;

public class AlterProductCommand implements Command<Boolean> {
    private Integer requestUserId;
    private Integer productId;
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private Optional<String> name;
    @Size(min = 3, max = 1000, message = "A descrição deve ter entre 3 e 1000 caracteres.")
    private Optional<String> description;
    private Optional<Double> price;
    private Optional<Integer> amount;
    public Integer getRequestUserId() {
        return requestUserId;
    }
    public void setRequestUserId(Integer requestUserId) {
        this.requestUserId = requestUserId;
    }
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
}
