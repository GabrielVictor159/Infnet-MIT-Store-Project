package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.Product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product.CreateProductCommand;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.UserRulesEnum;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Owner;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Product;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.OwnerRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.ProductRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Specifications.OwnerSpecification;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.ForbiddenException;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
import jakarta.transaction.Transactional;

@Component
public class CreateProductHandler implements CommandHandler<CreateProductCommand, Integer> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    @Transactional
    public Integer handle(CreateProductCommand command) {
        var requestUser = userRepository.findById(command.getRequestUserId());
        var specOwner = OwnerSpecification.findByIdWithUser(command.getOwnerId());
        var owner = ownerRepository.findOne(specOwner);
        if(!owner.isPresent()){
            throw new OperationException("Não foi possivel encontrar o vendedor");
        }
        if(requestUser.get().getRule()!=UserRulesEnum.Admin && owner.get().getUser().getId()!=requestUser.get().getId()){
            throw new ForbiddenException("Você não tem permissão de criar um produto vinculado a esse vendedor");
        }

        var newProduct = new Product();
        List<Owner> ownerAssociation = new ArrayList<Owner>();
        ownerAssociation.add(owner.get());
        newProduct.setOwners(ownerAssociation);
        BeanUtils.copyProperties(command, newProduct);
        newProduct = productRepository.save(newProduct);
        return newProduct.getId();
    }
}