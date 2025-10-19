package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.Owner;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Owner.GetOwnerCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.DTO.Owner.GetOwnerDTO;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Owner;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.OwnerRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Specifications.OwnerSpecification;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
import jakarta.transaction.Transactional;

@Component
public class GetOwnerHandler implements CommandHandler<GetOwnerCommand, GetOwnerDTO> {

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    @Transactional
    public GetOwnerDTO handle(GetOwnerCommand command) {
        Specification<Owner> spec = OwnerSpecification.findByIdWithUser(command.getId());
        var owner = ownerRepository.findOne(spec);
        if(!owner.isPresent()){
            throw new OperationException("Vendedor não encontrado");
        }

        var result = new GetOwnerDTO();
        BeanUtils.copyProperties(owner.get(), result);
        result.setUserId(owner.get().getUser().getId());
        return result;
    }
}
