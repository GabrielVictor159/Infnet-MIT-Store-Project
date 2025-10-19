package br.gabriel.infnet.gabrielvictorapi.Application.Commands.Owner;

import java.util.List;
import java.util.Optional;

import br.gabriel.infnet.gabrielvictorapi.Application.DTO.Owner.GetOwnerDTO;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.SearchPageSizeEnum;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SeachOwnerCommand implements Command<List<GetOwnerDTO>> {
    private Optional<Integer> id;
    private Optional<String> name;
    private Optional<String> cnpj;
    private Optional<String> address;
    private Optional<String> cep;
    private Optional<String> email;
    @NotNull
    @NotEmpty
    private Integer page;
    @NotNull
    private SearchPageSizeEnum size;
    public Optional<Integer> getId() {
        return id;
    }
    public void setId(Optional<Integer> id) {
        this.id = id;
    }
    public Optional<String> getName() {
        return name;
    }
    public void setName(Optional<String> name) {
        this.name = name;
    }
    public Optional<String> getCnpj() {
        return cnpj;
    }
    public void setCnpj(Optional<String> cnpj) {
        this.cnpj = cnpj;
    }
    public Optional<String> getAddress() {
        return address;
    }
    public void setAddress(Optional<String> address) {
        this.address = address;
    }
    public Optional<String> getCep() {
        return cep;
    }
    public void setCep(Optional<String> cep) {
        this.cep = cep;
    }
    public Optional<String> getEmail() {
        return email;
    }
    public void setEmail(Optional<String> email) {
        this.email = email;
    }
    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public SearchPageSizeEnum getSize() {
        return size;
    }
    public void setSize(SearchPageSizeEnum size) {
        this.size = size;
    }

}
