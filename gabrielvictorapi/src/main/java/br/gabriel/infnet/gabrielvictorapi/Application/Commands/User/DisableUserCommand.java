package br.gabriel.infnet.gabrielvictorapi.Application.Commands.User;

import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;
import jakarta.validation.constraints.NotNull;

public class DisableUserCommand implements Command<Boolean> {
    @NotNull
    private Integer id;
    @NotNull
    private Integer requestUserId;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getRequestUserId() {
        return requestUserId;
    }
    public void setRequestUserId(Integer requestUserId) {
        this.requestUserId = requestUserId;
    }
}
