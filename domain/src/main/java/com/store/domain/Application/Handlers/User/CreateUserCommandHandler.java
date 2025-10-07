package com.store.domain.Application.Handlers.User;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.store.domain.Application.Commands.Email.ConfirmUserEmailCommand;
import com.store.domain.Application.Commands.User.CreateUserCommand;
import com.store.domain.Application.DTO.User.CreateUserDTO;
import com.store.domain.Domain.Models.User;
import com.store.domain.Infraestructure.Repositories.UserRepository;
import com.store.domain.Shared.Exceptions.OperationException;
import com.store.domain.Shared.MediatorPattern.CommandHandler;
import com.store.domain.Shared.MediatorPattern.Mediator;
import org.springframework.context.annotation.Lazy;

import jakarta.transaction.Transactional;

import java.util.UUID;

@Component
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand, CreateUserDTO> {

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
