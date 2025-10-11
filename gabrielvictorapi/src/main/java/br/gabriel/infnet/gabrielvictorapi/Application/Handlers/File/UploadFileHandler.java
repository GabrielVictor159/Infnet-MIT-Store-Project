package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.File;

import java.util.LinkedHashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.File.GetOneFileCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.File.UploadFileCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.DTO.File.UploadFileDTO;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.UserRulesEnum;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Files;
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
        var ownersRequestUser = ownerRepository.findActiveOwnersWithProducts(requestUser.get());
        var users = userRepository.findAllById(command.getUsersId());
        var products = productRepository.findAllById(command.getProductsId());
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

        if (requestUser.get().getRule() != UserRulesEnum.Admin && !products.isEmpty()) {
            var ownerProductIds = ownersRequestUser.stream()
                .flatMap(owner -> owner.getProducts().stream())
                .map(product -> product.getId())
                .distinct()
                .toList();

            var unauthorizedProducts = products.stream()
                .filter(product -> !ownerProductIds.contains(product.getId()))
                .map(product -> product.getId())
                .toList();

            if (!unauthorizedProducts.isEmpty()) {
                throw new ForbiddenException("O usuário não possui permissão sobre os produtos: " + unauthorizedProducts);
            }
        }

        if (requestUser.get().getRule() != UserRulesEnum.Admin) {
            var requestUserId = requestUser.get().getId();

            var otherUsers = users.stream()
                .map(User::getId)
                .filter(id -> !id.equals(requestUserId))
                .toList();

            if (!otherUsers.isEmpty()) {
                throw new ForbiddenException("O usuário não possui permissão para associar outros usuários: " + otherUsers);
            }
        }

        if(requestUser.get().getRule() != UserRulesEnum.Admin && users.isEmpty() && products.isEmpty()){
            throw new ForbiddenException("Você não tem permissão para adicionar um arquivo sem associação");
        }
        
        var file = new Files(); 
        file.setContent(command.getContent());
        file.setFileName(command.getFileName());
        file.setFileType(command.getFileType());
        file.setUsers(new LinkedHashSet<>(users));
        file.setProducts(new LinkedHashSet<>(products));

        file = filesRepository.save(file);

        file = filesRepository.save(file); 

        for (var user : users) {
            user.getFiles().add(file);
        }
        userRepository.saveAll(users);

        for (var product : products) {
            product.getFiles().add(file);
        }
        productRepository.saveAll(products);

        var result = new UploadFileDTO();
        result.setId(file.getId());

       
        return result;
    }
}
