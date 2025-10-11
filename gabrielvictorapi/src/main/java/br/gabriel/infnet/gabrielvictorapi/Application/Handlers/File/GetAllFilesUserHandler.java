package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.File.GetAllFilesUserCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.DTO.File.GetAllFileUserDTO;
import br.gabriel.infnet.gabrielvictorapi.Application.Mappers.Files.FilesMapperResponseExtensions;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.FileTypeEnum;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.UserRulesEnum;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.FilesRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.ForbiddenException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;

@Component
public class GetAllFilesUserHandler implements CommandHandler<GetAllFilesUserCommand, List<GetAllFileUserDTO>> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FilesRepository filesRepository;

    @Override
    public List<GetAllFileUserDTO> handle(GetAllFilesUserCommand command) {
        var requestUser = userRepository.findById(command.getUserRequestId());
        List<FileTypeEnum> fileTypes = Arrays.stream(FileTypeEnum.values())
            .filter(f -> f.getRequiredRole() == UserRulesEnum.User)
            .collect(Collectors.toList());

        if(command.getUserId()==command.getUserRequestId() && !command.getFileType().isPresent()){
            fileTypes = Arrays.asList(FileTypeEnum.values());
        }
        if(command.getUserId()!=command.getUserRequestId() && requestUser.get().getRule()==UserRulesEnum.Admin && !command.getFileType().isPresent()){
            fileTypes = Arrays.asList(FileTypeEnum.values());
        }
        if(command.getUserId() != command.getUserRequestId() && command.getFileType().isPresent() && requestUser.get().getRule()!=UserRulesEnum.Admin && command.getFileType().get().getRequiredRole()==UserRulesEnum.Admin){
            throw new ForbiddenException("Você não tem permissão para visualizar esse tipo de arquivo desse usuário.");
        }

        if(command.getFileType().isPresent()){
            fileTypes = new ArrayList<FileTypeEnum>();
            fileTypes.add(command.getFileType().get());
        }
        
        List<GetAllFileUserDTO> result = new ArrayList<GetAllFileUserDTO>();
        if(command.getContent()){
            var filesUser = filesRepository.findFilesByUserAndTypes(command.getUserId(), fileTypes);
            result = FilesMapperResponseExtensions.toGetAllFileUserDTO(filesUser);
        }
        else{
            var filesUser = filesRepository.findFileSummariesByUserAndTypes(command.getUserId(), fileTypes);
            result = FilesMapperResponseExtensions.toGetAllFileUserDTOFromFileNotContent(filesUser);
        }
        return result;
    }
}

