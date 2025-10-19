package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.Owner;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Owner.AlterOwnerCommand;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.UserRulesEnum;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Owner;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.OwnerRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Specifications.OwnerSpecification;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.ForbiddenException;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
import jakarta.transaction.Transactional;

@Component
public class AlterOwnerHandler implements CommandHandler<AlterOwnerCommand, Boolean> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    @Transactional
    public Boolean handle(AlterOwnerCommand command) {
        var requestUser = userRepository.findById(command.getRequestUserId());
        Specification<Owner> ownerSpecification = OwnerSpecification.findByIdWithUser(command.getId());
        var owner = ownerRepository.findOne(ownerSpecification);
        if(!owner.isPresent()){
            throw new OperationException("Vendedor não encontrado");
        }
        if(requestUser.get().getRule()!=UserRulesEnum.Admin && owner.get().getUser().getId()!=requestUser.get().getId()){
            throw new ForbiddenException("Você não tem permissão de alterar o vendedor");
        }

        BeanUtils.copyProperties(command, owner.get());
        owner.get().setUpdatedAt(LocalDateTime.now());
        ownerRepository.save(owner.get());
        return true;
    }
}
