package br.gabriel.infnet.gabrielvictorapi.Application.Commands.Email;

import java.util.UUID;

import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;

public class ConfirmUserEmailCommand implements Command<Boolean> {
    private String Email;
    private UUID ConfirmId;
    
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public UUID getConfirmId() {
        return ConfirmId;
    }
    public void setConfirmId(UUID confirmId) {
        ConfirmId = confirmId;
    }

}
