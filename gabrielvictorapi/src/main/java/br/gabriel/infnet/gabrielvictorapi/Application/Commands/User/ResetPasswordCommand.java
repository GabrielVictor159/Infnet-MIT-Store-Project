package br.gabriel.infnet.gabrielvictorapi.Application.Commands.User;

import java.util.UUID;

import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordCommand implements Command<String> {
    private UUID id;
    @NotBlank(message = "A senha não pode ser nula ou vazia.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    private String password;
    @NotBlank(message = "A senha não pode ser nula ou vazia.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    private String confirmPassword;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
