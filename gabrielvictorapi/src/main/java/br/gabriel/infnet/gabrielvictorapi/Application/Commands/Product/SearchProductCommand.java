package br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product;

import java.util.List;
import java.util.Optional;

import br.gabriel.infnet.gabrielvictorapi.Application.DTO.Product.ProductDTO;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.SearchPageSizeEnum;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SearchProductCommand implements Command<List<ProductDTO>> {
    private Optional<Integer> id;
    private Optional<String> name;
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
