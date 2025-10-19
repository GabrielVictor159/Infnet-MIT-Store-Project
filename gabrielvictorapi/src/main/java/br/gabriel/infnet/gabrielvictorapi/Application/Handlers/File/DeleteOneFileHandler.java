package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.File.DeleteOneFileCommand;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.UserRulesEnum;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Owner;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.FilesRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.OwnerRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Specifications.OwnerSpecification;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.ForbiddenException;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
import jakarta.transaction.Transactional;

@Component
public class DeleteOneFileHandler implements CommandHandler<DeleteOneFileCommand, Boolean> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    @Transactional
    public Boolean handle(DeleteOneFileCommand command) {
        var requestUser = userRepository.findById(command.getRequestUser());
        Specification<Owner> specOwners = OwnerSpecification.findActiveOwnersWithProducts(requestUser.get());
        var ownersUser = ownerRepository.findAll(specOwners);

        var file = filesRepository.findByIdWithDeepAssociations(command.getId());
        if (!file.isPresent())
            throw new OperationException("Não foi possivel localizar o arquivo com o id especificado!");
        
        if(requestUser.get().getRule()!=UserRulesEnum.Admin){
           boolean hasPermissionOwner= file.get().getOwners().stream()
            .anyMatch(owner -> ownersUser.stream()
                .anyMatch(owner2 -> owner2.getId().equals(owner.getId())));

           boolean hasPermissionProduct = file.get().getProducts().stream()
            .anyMatch(product -> ownersUser.stream()
                .anyMatch(owner -> owner.getProducts().contains(product)));
            
             boolean hasPermissionUser = file.get().getUsers().stream()
            .anyMatch(user -> user.getId().equals(requestUser.get().getId()));

            if(!hasPermissionProduct && !hasPermissionUser && !hasPermissionOwner){
                throw new ForbiddenException("O usuário não possui permissão para excluir este arquivo.");
            }
        }
        filesRepository.deleteProductAssociations(file.get().getId());
        filesRepository.deleteUserAssociations(file.get().getId());
        filesRepository.deleteOwnersAssociations(file.get().getId());
        filesRepository.deleteById(file.get().getId());

        return true;
    }
}
