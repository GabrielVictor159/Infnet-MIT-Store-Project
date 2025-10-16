package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.File;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.File.GetOneFileCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.DTO.File.GetOneFileDTO;
import br.gabriel.infnet.gabrielvictorapi.Application.Mappers.Files.FilesMapperResponseExtensions;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.UserRulesEnum;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.User;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.FilesRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.ForbiddenException;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;

@Component
public class GetOneFileHandler implements CommandHandler<GetOneFileCommand, GetOneFileDTO> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FilesRepository filesRepository;

    @Override
    public GetOneFileDTO handle(GetOneFileCommand command) {
        var requestUser = userRepository.findById(command.getUserRequestId());
        GetOneFileDTO result = null;
        List<Integer> usersId = new ArrayList();

        var file = filesRepository.findByIdWithDeepAssociations(command.getId());
        if (!file.isPresent())
            throw new OperationException("Não foi possivel localizar o arquivo com o id especificado!");

        if(!file.get().getUsers().isEmpty())
            usersId = file.get().getUsers().stream()
                    .map(User::getId)
                    .collect(Collectors.toList());
        
        for (var owner : file.get().getOwners()) {
            usersId.add(owner.getUser().getId());
        }

        for (var product : file.get().getProducts()) {
            for (var owner : product.getOwners()){
                usersId.add(owner.getUser().getId());
            }
        }

        result = FilesMapperResponseExtensions.toGetOneFileDTO(file.get());

        if (usersId.stream().filter(item -> item == command.getUserRequestId()).findFirst() == null
                && requestUser.get().getRule() != UserRulesEnum.Admin
                && result.getFileType().getRequiredRole() == UserRulesEnum.Admin) {
            throw new ForbiddenException("Você não tem permissão para visualizar esse tipo de arquivo desse usuário.");
        }

        return result;
    }
}
