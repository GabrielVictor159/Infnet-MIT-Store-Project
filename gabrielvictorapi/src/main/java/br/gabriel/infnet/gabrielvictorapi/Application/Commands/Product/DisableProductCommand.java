package br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product;

import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;
import jakarta.validation.constraints.NotNull;

public class DisableProductCommand implements Command<Boolean> {
    @NotNull
    private Integer requestUser;
    @NotNull
    private Integer id;
    public Integer getRequestUser() {
        return requestUser;
    }
    public void setRequestUser(Integer requestUser) {
        this.requestUser = requestUser;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
