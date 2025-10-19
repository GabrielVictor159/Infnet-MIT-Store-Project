package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.Owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Owner.DisableOwnerCommand;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.UserRulesEnum;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Owner;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.OwnerRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Specifications.OwnerSpecification;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
import jakarta.transaction.Transactional;

@Component
public class DisableOwnerHandler implements CommandHandler<DisableOwnerCommand, Boolean> {

    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Boolean handle(DisableOwnerCommand command) {
        Specification<Owner> spec = OwnerSpecification.findByIdWithUser(command.getId());
        var owner = ownerRepository.findOne(spec);
        var requestUser = userRepository.findById(command.getRequestUser());
        if(!owner.isPresent()){
            throw new OperationException("Vendedor não encontrado");
        }
        if(owner.get().getUser().getId()!=command.getRequestUser() && requestUser.get().getRule()!=UserRulesEnum.Admin)
        owner.get().setIsActive(false);
        ownerRepository.save(owner.get());
        return true;
    }
}

