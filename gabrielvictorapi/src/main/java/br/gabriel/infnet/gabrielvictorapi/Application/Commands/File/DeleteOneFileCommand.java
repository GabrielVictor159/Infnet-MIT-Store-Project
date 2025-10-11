package br.gabriel.infnet.gabrielvictorapi.Application.Commands.File;

import java.util.UUID;

import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;
import jakarta.validation.constraints.NotNull;

public class DeleteOneFileCommand implements Command<Boolean>  {
    @NotNull
    private UUID id;
    @NotNull
    private Integer requestUser;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public Integer getRequestUser() {
        return requestUser;
    }
    public void setRequestUser(Integer requestUser) {
        this.requestUser = requestUser;
    }
}
