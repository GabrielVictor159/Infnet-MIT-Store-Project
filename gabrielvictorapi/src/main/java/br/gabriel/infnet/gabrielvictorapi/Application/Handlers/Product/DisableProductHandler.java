package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.Product;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product.DisableProductCommand;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.UserRulesEnum;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.ProductRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Specifications.ProductSpecification;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.ForbiddenException;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
import jakarta.transaction.Transactional;

@Component
public class DisableProductHandler implements CommandHandler<DisableProductCommand, Boolean> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public Boolean handle(DisableProductCommand command) {
        var requestUser = userRepository.findById(command.getRequestUser());
        var specProduct = ProductSpecification.findByIdWithDeepAssociations(command.getId());
        var product = productRepository.findOne(specProduct);
        if(!product.isPresent()){
            throw new OperationException("Produto não encontrado");
        }

        if(requestUser.get().getRule()!=UserRulesEnum.Admin && product.get().getOwners().stream().filter(item -> item.getUser().getId()==requestUser.get().getId()).findFirst()==null){
            throw new ForbiddenException("Você não tem permissão de alterar o produto vinculado a esses vendedores");
        }

        product.get().setIsActive(false);
        product.get().setUpdatedAt(LocalDateTime.now());
        
        productRepository.save(product.get());
        return true;
    }
}