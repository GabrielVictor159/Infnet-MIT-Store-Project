package br.gabriel.infnet.gabrielvictorapi.Application.Commands.User;

import java.util.Optional;
import java.util.UUID;

import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;
import jakarta.validation.constraints.NotBlank;

public class ForbiddenPasswordCommand implements Command<Optional<UUID>> {
    @NotBlank(message = "O email não pode ser nulo ou vazio.")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
