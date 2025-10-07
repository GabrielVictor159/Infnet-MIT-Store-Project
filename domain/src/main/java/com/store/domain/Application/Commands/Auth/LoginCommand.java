package com.store.domain.Application.Commands.Auth;

import com.store.domain.Application.DTO.Auth.LoginDTO;
import com.store.domain.Shared.MediatorPattern.Command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginCommand implements Command<LoginDTO> {
    
    @NotBlank(message = "O email não pode ser nulo ou vazio.")
    @Email(message = "O formato do email é inválido.")
    private String email;

    @NotBlank(message = "A senha não pode ser nula ou vazia.")
    private String password;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
