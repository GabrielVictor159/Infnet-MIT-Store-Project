package br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product;

import br.gabriel.infnet.gabrielvictorapi.Application.DTO.Product.ProductDTO;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;
import jakarta.validation.constraints.NotNull;

public class GetProductCommand implements Command<ProductDTO> {
    @NotNull
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
