package br.gabriel.infnet.gabrielvictorapi.Application.Commands.Email;

import java.util.UUID;

import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;

public class ForbiddenPasswordEmailCommand implements Command<Boolean> {
    private String Email;
    private UUID ForbiddenPasswordId;
    
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public UUID getForbiddenPasswordId() {
        return ForbiddenPasswordId;
    }
    public void setForbiddenPasswordId(UUID forbiddenPasswordId) {
        ForbiddenPasswordId = forbiddenPasswordId;
    }

}
