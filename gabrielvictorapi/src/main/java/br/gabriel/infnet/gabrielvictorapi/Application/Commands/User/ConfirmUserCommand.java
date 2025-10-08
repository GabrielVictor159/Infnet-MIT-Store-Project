package br.gabriel.infnet.gabrielvictorapi.Application.Commands.User;

import java.util.UUID;

import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;

import jakarta.validation.constraints.NotNull;

public class ConfirmUserCommand implements Command<String> {
    @NotNull(message = "O id não pode ser nulo ou vazio.")
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
