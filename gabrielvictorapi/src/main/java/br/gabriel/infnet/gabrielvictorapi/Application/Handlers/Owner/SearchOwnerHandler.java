package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.Owner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Pageable;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Owner.SeachOwnerCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.DTO.Owner.GetOwnerDTO;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Owner;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.OwnerRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Specifications.OwnerSpecification;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
import jakarta.transaction.Transactional;

@Component
public class SearchOwnerHandler implements CommandHandler<SeachOwnerCommand, List<GetOwnerDTO>> {

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    @Transactional
    public List<GetOwnerDTO> handle(SeachOwnerCommand command) {
        Specification<Owner> spec = OwnerSpecification.findByCriteria(
            command.getId(),
            command.getName(),
            command.getCnpj(),
            command.getAddress(),
            command.getCep(),
            command.getEmail()
        );

        Pageable pageable = PageRequest.of(
            command.getPage(),
            command.getSize().getValue()
        );
        var owners = ownerRepository.findAll(spec, pageable);

        List<GetOwnerDTO> result = new ArrayList<GetOwnerDTO>();

        for (Owner owner : owners) {
            var itemResult = new GetOwnerDTO();
            BeanUtils.copyProperties(owner, itemResult);
            itemResult.setUserId(owner.getUser().getId());
            result.add(itemResult);
        }
        return result;
    }
}
