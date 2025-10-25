package br.gabriel.infnet.gabrielvictorapi.Api.Mappers.Product;

import org.springframework.beans.BeanUtils;

import br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.Product.AlterProductRequest;
import br.gabriel.infnet.gabrielvictorapi.Api.Controllers.Requests.Product.CreateProductRequest;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product.AlterProductCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product.CreateProductCommand;

public final class ProductMapperRequestsExtension {
    public static CreateProductCommand toCreateProductCommand(CreateProductRequest req){
        var command = new CreateProductCommand();
        BeanUtils.copyProperties(req, command);
        return command;
    }
    public static AlterProductCommand toAlterProductCommand(AlterProductRequest req){
        var command = new AlterProductCommand();
        BeanUtils.copyProperties(req, command);
        return command;
    }
}
