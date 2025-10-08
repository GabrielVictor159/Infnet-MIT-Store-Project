package br.gabriel.infnet.gabrielvictorapi.Application.Commands.User;

import java.util.Date;

import br.gabriel.infnet.gabrielvictorapi.Shared.Anotations.AdultDate;
import br.gabriel.infnet.gabrielvictorapi.Shared.Anotations.CPF;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AlterUserCommand implements Command<Boolean> {

    private Integer id;
    @NotNull
    private Integer requestUserId;
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String name;
    @CPF
    private String cpf;
    @Pattern(regexp = "^[0-9\\s\\-()]*$", message = "O telefone contém caracteres inválidos.")
    private String phone;
    @AdultDate
    private Date birthDate;

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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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

