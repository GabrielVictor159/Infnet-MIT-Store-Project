package br.gabriel.infnet.gabrielvictorapi.Application.Commands.Owner;

import java.util.Optional;

import org.hibernate.validator.constraints.br.CNPJ;

import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AlterOwnerCommand implements Command<Boolean> {
    @NotNull
    private Integer requestUserId;
    @NotNull
    private Integer id;
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private Optional<String> name;
    @Size(min = 3, max = 1000, message = "A descrição deve ter entre 3 e 1000 caracteres.")
    private Optional<String> description;
    @Pattern(regexp = "^[0-9\\s\\-()]*$", message = "O telefone contém caracteres inválidos.")
    private Optional<String> contactPhone;
    @Email(message = "O formato do email é inválido.")
    private Optional<String> contactEmail;
    @CNPJ
    private Optional<String> cnpj;
    private Optional<String> cep;
    private Optional<String> address;
    public Integer getRequestUserId() {
        return requestUserId;
    }
    public void setRequestUserId(Integer requestUserId) {
        this.requestUserId = requestUserId;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Optional<String> getName() {
        return name;
    }
    public void setName(Optional<String> name) {
        this.name = name;
    }
    public Optional<String> getDescription() {
        return description;
    }
    public void setDescription(Optional<String> description) {
        this.description = description;
    }
    public Optional<String> getContactPhone() {
        return contactPhone;
    }
    public void setContactPhone(Optional<String> contactPhone) {
        this.contactPhone = contactPhone;
    }
    public Optional<String> getContactEmail() {
        return contactEmail;
    }
    public void setContactEmail(Optional<String> contactEmail) {
        this.contactEmail = contactEmail;
    }
    public Optional<String> getCnpj() {
        return cnpj;
    }
    public void setCnpj(Optional<String> cnpj) {
        this.cnpj = cnpj;
    }
    public Optional<String> getCep() {
        return cep;
    }
    public void setCep(Optional<String> cep) {
        this.cep = cep;
    }
    public Optional<String> getAddress() {
        return address;
    }
    public void setAddress(Optional<String> address) {
        this.address = address;
    }
}
