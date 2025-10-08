package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.User;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Email.ConfirmUserEmailCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Commands.User.CreateUserCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.DTO.User.CreateUserDTO;
import br.gabriel.infnet.gabrielvictorapi.Domain.Enums.UserRulesEnum;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.User;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.UserRepository;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Mediator;

import org.springframework.context.annotation.Lazy;

import jakarta.transaction.Transactional;

import java.util.UUID;

@Component
public class CreateUserHandler implements CommandHandler<CreateUserCommand, CreateUserDTO> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    @Lazy
    private Mediator mediator; 

    @Override
    @Transactional
    public CreateUserDTO handle(CreateUserCommand command) {
        if(userRepository.findByEmail(command.getEmail()).isPresent()){
            throw new OperationException("Ja existe um usuário com esse email");
        }

        var newUser = new User();
        BeanUtils.copyProperties(command, newUser);
        newUser.setIsActive(false);
        newUser.setCreateId(UUID.randomUUID());
        newUser.setRule(UserRulesEnum.User);

        var confirmUserEmailCommand = new ConfirmUserEmailCommand();
        confirmUserEmailCommand.setEmail(command.getEmail());
        confirmUserEmailCommand.setConfirmId(newUser.getCreateId());

        var sucessSendEmail = mediator.Handler(confirmUserEmailCommand);

        if(sucessSendEmail){
            userRepository.save(newUser);
        } else {
            throw new OperationException("Não foi possível enviar o email de confirmação.");
        }

        return new CreateUserDTO(String.format("Solicitação do usuário criada com sucesso, verifique o email %s", command.getEmail()));
    }
}
