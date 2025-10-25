package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.Product;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product.AlterProductCommand;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.UserRulesEnum;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.ProductRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Specifications.ProductSpecification;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.ForbiddenException;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
import jakarta.transaction.Transactional;

@Component
public class AlterProductHandler implements CommandHandler<AlterProductCommand, Boolean> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public Boolean handle(AlterProductCommand command) {
        var requestUser = userRepository.findById(command.getRequestUserId());
        var specProduct = ProductSpecification.findByIdWithDeepAssociations(command.getProductId());
        var product = productRepository.findOne(specProduct);
        if(!product.isPresent()){
            throw new OperationException("Produto não encontrado");
        }

        if(requestUser.get().getRule()!=UserRulesEnum.Admin && product.get().getOwners().stream().filter(item -> item.getUser().getId()==requestUser.get().getId()).findFirst()==null){
            throw new ForbiddenException("Você não tem permissão de alterar o produto vinculado a esses vendedores");
        }

        if (command.getName().isPresent()) product.get().setName(command.getName().get());
        if (command.getDescription().isPresent()) product.get().setDescription(command.getDescription().get());
        if (command.getDescription().isPresent()) product.get().setDescription(command.getDescription().get());
        if (command.getPrice().isPresent()) product.get().setPrice(command.getPrice().get());
        if (command.getAmount().isPresent()) product.get().setAmount(command.getAmount().get());

        product.get().setUpdatedAt(LocalDateTime.now());
        productRepository.save(product.get());
        return true;
    }
}
