package br.gabriel.infnet.gabrielvictorapi.Application.Commands.User;

import java.util.Date;

import br.gabriel.infnet.gabrielvictorapi.Application.DTO.User.CreateUserDTO;
import br.gabriel.infnet.gabrielvictorapi.Shared.Anotations.AdultDate;
import br.gabriel.infnet.gabrielvictorapi.Shared.Anotations.CPF;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateUserCommand implements Command<CreateUserDTO> {
    @NotBlank(message = "O nome não pode ser nulo ou vazio.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String name;
    @NotBlank(message = "A senha não pode ser nula ou vazia.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    private String password;
    @NotBlank(message = "O email não pode ser nulo ou vazio.")
    @Email(message = "O formato do email é inválido.")
    private String email;
    @NotNull
    @CPF
    private String cpf;
    @Pattern(regexp = "^[0-9\\s\\-()]*$", message = "O telefone contém caracteres inválidos.")
    private String phone;
    @AdultDate
    @NotNull
    private Date birthDate;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    
}
