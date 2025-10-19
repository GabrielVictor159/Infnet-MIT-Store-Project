package br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product;

import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateProductCommand implements Command<Integer> {
    private Integer requestUserId;
    @NotBlank(message = "O nome não pode ser nulo ou vazio.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String name;
    @NotBlank(message = "A descrição não pode ser nulo ou vazio.")
    @Size(min = 3, max = 1000, message = "A descrição deve ter entre 3 e 1000 caracteres.")
    private String description;
    @NotNull
    @NotBlank
    private Double price;
    @NotNull
    private Integer amount;
    @NotNull
    @NotBlank
    private Integer ownerId;
    public Integer getRequestUserId() {
        return requestUserId;
    }
    public void setRequestUserId(Integer requestUserId) {
        this.requestUserId = requestUserId;
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
    public Integer getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
}
