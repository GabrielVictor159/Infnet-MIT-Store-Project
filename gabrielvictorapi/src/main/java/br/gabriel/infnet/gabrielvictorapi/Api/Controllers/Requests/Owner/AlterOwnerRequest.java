package br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.Owner;

import java.util.Optional;

public class AlterOwnerRequest {
    private Integer id;
    private Optional<String> name;
    private Optional<String> description;
    private Optional<String> contactPhone;
    private Optional<String> contactEmail;
    private Optional<String> cnpj;
    private Optional<String> cep;
    private Optional<String> address;
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
