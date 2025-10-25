package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.Product;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product.GetProductCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.DTO.Product.ProductDTO;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.ProductRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Specifications.ProductSpecification;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
import jakarta.transaction.Transactional;

public class GetProductHandler implements CommandHandler<GetProductCommand, ProductDTO>  {
    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public ProductDTO handle(GetProductCommand command) {
        var specProduct = ProductSpecification.findById(command.getId());
        var product = productRepository.findOne(specProduct);
        if(!product.isPresent()){
            throw new OperationException("Produto não encontrado");
        }
        var result = new ProductDTO();
        BeanUtils.copyProperties(product, result);
        return result;
    }
}
