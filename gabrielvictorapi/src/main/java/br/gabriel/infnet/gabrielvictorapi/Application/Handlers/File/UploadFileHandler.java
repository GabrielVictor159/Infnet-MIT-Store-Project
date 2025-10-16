package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.File;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.File.GetOneFileCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.File.UploadFileCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.DTO.File.UploadFileDTO;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.UserRulesEnum;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Files;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Owner;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.User;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.FilesRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.OwnerRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.ProductRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.ForbiddenException;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
import jakarta.transaction.Transactional;

@Component
public class UploadFileHandler implements CommandHandler<UploadFileCommand, UploadFileDTO> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    @Transactional
    public UploadFileDTO handle(UploadFileCommand command) {
        var requestUser = userRepository.findById(command.getUserRequestId());

        var users = userRepository.findAllById(command.getUsersId());
        var products = productRepository.findByInIdWithDeepAssociations(command.getProductsId());
        var owners = ownerRepository.findByInIdWithAssociations(command.getOwnersId());

        var foundUserIds = users.stream().map(User::getId).toList();
        var missingUserIds = command.getUsersId().stream()
            .filter(id -> !foundUserIds.contains(id))
            .toList();

        if (!missingUserIds.isEmpty()) {
            throw new OperationException("IDs de usuários inválidos: " + missingUserIds);
        }
        var foundProductIds = products.stream().map(p -> p.getId()).toList();
        var missingProductIds = command.getProductsId().stream()
            .filter(id -> !foundProductIds.contains(id))
            .toList();

        if (!missingProductIds.isEmpty()) {
            throw new OperationException("IDs de produtos inválidos: " + missingProductIds);
        }

        var foundOwnersIds = owners.stream().map(p -> p.getId()).toList();
        var missingOwnerIds = command.getOwnersId().stream()
            .filter(id -> !foundOwnersIds.contains(id))
            .toList();

        if (!missingOwnerIds.isEmpty()) {
            throw new OperationException("IDs de vendedores inválidos: " + missingOwnerIds);
        }

        List<Integer> usersId = new ArrayList();
        
        for (var user : users) {
            usersId.add(user.getId());
        }

        for (var owner : owners) {
            usersId.add(owner.getUser().getId());
        }

        for (var product : products) {
            for (var owner : product.getOwners()){
                usersId.add(owner.getUser().getId());
            }
        }

        if(requestUser.get().getRule() != UserRulesEnum.Admin && users.isEmpty() && products.isEmpty()){
            throw new ForbiddenException("Você não tem permissão para adicionar um arquivo sem associação");
        }

        if(requestUser.get().getRule() != UserRulesEnum.Admin && usersId.stream().filter(item -> item == command.getUserRequestId()).findFirst() == null){
            throw new ForbiddenException("Você não tem permissão para adicionar um arquivo as entidades selecionadas");
        }
        
        var file = new Files(); 
        file.setContent(command.getContent());
        file.setFileName(command.getFileName());
        file.setFileType(command.getFileType());
        file.setUsers(new LinkedHashSet<>(users));
        file.setProducts(new LinkedHashSet<>(products));
        file.setOwners(new LinkedHashSet<>(owners));

        file = filesRepository.save(file);

        for (var user : users) {
            user.getFiles().add(file);
        }
        userRepository.saveAll(users);

        for (var product : products) {
            product.getFiles().add(file);
        }
        productRepository.saveAll(products);

        for (var owner : owners) {
            owner.getFiles().add(file);
        }

        var result = new UploadFileDTO();
        result.setId(file.getId());
       
        return result;
    }
}
